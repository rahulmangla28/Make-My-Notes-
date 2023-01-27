package com.genius_koder.makemynotes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class RegisterActivity : AppCompatActivity() {

    lateinit var msignupEmail : EditText
    lateinit var msignupPassword : EditText
    lateinit var mconformsignupPassword : EditText
    lateinit var mbtnRegister : TextView
    lateinit var malreadyhaveAccount : TextView

    lateinit var firebaseAuth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        // hides toolbar
        supportActionBar?.hide()

        msignupEmail = findViewById(R.id.signupEmail)
        msignupPassword = findViewById(R.id.signupPassword)
        mconformsignupPassword = findViewById(R.id.conformsignupPassword)
        mbtnRegister = findViewById(R.id.btnRegister)
        malreadyhaveAccount = findViewById(R.id.alreadyHaveAccount)

        // get instance of FirebaseAuth
        firebaseAuth = FirebaseAuth.getInstance()

        // Register the new user after checking all credentials
        mbtnRegister.setOnClickListener {
            val email = msignupEmail.text.toString().trim()
            val password = msignupPassword.text.toString().trim()
            val conformPassword = mconformsignupPassword.text.toString().trim()

            if(email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this,"All fields are mandatory",Toast.LENGTH_SHORT).show()
            }else if(password.length < 7) {
                Toast.makeText(this,"Password is too short (Length >= 7)",Toast.LENGTH_SHORT).show()
            }else if(password!=conformPassword) {
                Toast.makeText(this,"Entered password are not same",Toast.LENGTH_SHORT).show()
            }
            else{
                // register the user to firebase
                firebaseAuth.createUserWithEmailAndPassword(email,password)
                    .addOnCompleteListener(this) {  task->
                        if(task.isSuccessful) {
                            Toast.makeText(this,"Registration successful",Toast.LENGTH_SHORT).show()
                            sendEmailVerification()
                        }else {
                            Toast.makeText(this,"Registration unsuccessful",Toast.LENGTH_SHORT).show()
                        }
                }
            }
        }

        // moves user to the Login Page
        malreadyhaveAccount.setOnClickListener {
            val intent = Intent(this, LoginPage::class.java)
            startActivity(intent)
        }
    }

    // send email verification to entered email to ensure whether user entered correct email or not
    private fun sendEmailVerification() {
        val firebaseUser = firebaseAuth.currentUser
        if(firebaseUser!=null) {
            firebaseUser.sendEmailVerification().addOnCompleteListener(this) { task->
                Toast.makeText(this,"Verification Email is sent \n Verify it and Login again",Toast.LENGTH_SHORT).show()
                firebaseAuth.signOut()
                val intent = Intent(this, LoginPage::class.java)
                startActivity(intent)
            }
        }else {
            Toast.makeText(this,"Failed to send Verification Email",Toast.LENGTH_SHORT).show()
        }
    }
}