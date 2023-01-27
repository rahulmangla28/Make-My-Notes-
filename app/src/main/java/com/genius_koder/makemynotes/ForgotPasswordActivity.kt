package com.genius_koder.makemynotes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class ForgotPasswordActivity : AppCompatActivity() {

    lateinit var minputRegisteredEmail : EditText
    lateinit var mbtnRecover : TextView
    lateinit var mgobacktoLogin : TextView

    lateinit var firebaseAuth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        // hides toolbar
        supportActionBar?.hide()

        minputRegisteredEmail=findViewById(R.id.inputRegisteredEmail)
        mbtnRecover = findViewById(R.id.btnrecover)
        mgobacktoLogin = findViewById(R.id.gobacktoLogin)

        // get instance of FirebaseAuth
        firebaseAuth = FirebaseAuth.getInstance()

        // on click user moves back to login page
        mgobacktoLogin.setOnClickListener{
            val intent = Intent(this, LoginPage::class.java)
            startActivity(intent)
        }

        // send a password recovery email to the user
        mbtnRecover.setOnClickListener {
            val email : String = minputRegisteredEmail.text.toString().trim()
            if(email.isEmpty()) {
                Toast.makeText(this,"Enter your registered email first",Toast.LENGTH_SHORT).show()
            }else {
                // we have to send password recover email
                firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener(this){ task->
                    if(task.isSuccessful) {
                        Toast.makeText(this,"Email Sent,Recover your password using Email",Toast.LENGTH_SHORT).show()
                        finish()
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                    }else {
                        Toast.makeText(this,"Email is wrong or Account does not exist",Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

    }
}