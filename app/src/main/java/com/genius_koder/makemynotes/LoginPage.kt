package com.genius_koder.makemynotes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class LoginPage : AppCompatActivity() {

    lateinit var minputEmail : EditText
    lateinit var minputPassword : EditText
    lateinit var mforgotPassword : TextView
    lateinit var mbtnLogin : TextView
    lateinit var mcreateNewAccount : TextView

    lateinit var firebaseAuth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_page)

        // hides toolbar
        supportActionBar?.hide()

        minputEmail = findViewById(R.id.inputEmail)
        minputPassword = findViewById(R.id.inputPassword)
        mbtnLogin = findViewById(R.id.btnLogin)
        mforgotPassword = findViewById(R.id.forgotPassword)
        mcreateNewAccount = findViewById(R.id.createNewAccount)

        // get instance of FirebaseAuth
        firebaseAuth = FirebaseAuth.getInstance()
        val firebaseUser = firebaseAuth.currentUser

        // if user is already logged in no need to perform the login activity
        if(firebaseUser!=null) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        // login the user after verification of the entered credentials
        mbtnLogin.setOnClickListener {
            val email = minputEmail.text.toString().trim()
            val password = minputPassword.text.toString().trim()

            if(email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this,"All fields are mandatory",Toast.LENGTH_SHORT).show()
            }else if(password.length < 7) {
                Toast.makeText(this,"Password is too short (Length >= 7)",Toast.LENGTH_SHORT).show()
            }else {
                // Login the user
                firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this) { task->
                    if(task.isSuccessful) {
                        checkEmailVerification()
                    }else {
                        Toast.makeText(this,"Account does not exist",Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        // moves user to the ForgotPasswordActivity where user can set password again
        mforgotPassword.setOnClickListener {
            val intent = Intent(this, ForgotPasswordActivity::class.java)
            startActivity(intent)
        }

        // moves the new user to the Register activity when user can register and thereafter login the app
        mcreateNewAccount.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

    }

    // verifies the Email
    private fun checkEmailVerification() {
        val firebaseUser = firebaseAuth.currentUser
        if (firebaseUser != null) {
            if(firebaseUser.isEmailVerified) {
                Toast.makeText(this,"Logged In",Toast.LENGTH_SHORT).show()
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }else {
                Toast.makeText(this,"Verify your Email first",Toast.LENGTH_SHORT).show()
                firebaseAuth.signOut()
            }
        }
    }
}