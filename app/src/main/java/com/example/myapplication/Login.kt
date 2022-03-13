package com.example.myapplication.ui

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.MainActivity
import com.example.myapplication.R
import com.google.firebase.auth.FirebaseAuth

class Login : AppCompatActivity() {
    private var login: Button? = null
    private var name: EditText? = null
    private var password: EditText? = null
    private var auth: FirebaseAuth? = null
    private var firebaseAuthStateListener: FirebaseAuth.AuthStateListener? = null
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.log_in_and_out)
        auth = FirebaseAuth.getInstance()

        firebaseAuthStateListener = FirebaseAuth.AuthStateListener {
            FirebaseAuth.getInstance().currentUser
        }

        login = findViewById<View>(R.id.button2) as Button
        name = findViewById<View>(R.id.name) as EditText
        password = findViewById<View>(R.id.password) as EditText

        //Login user
        login!!.setOnClickListener {
            val email = name?.text.toString()
            val password = password?.text.toString()
            if(auth!!.currentUser != null) {
                auth!!.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this@Login) { task -> //Check is registration fails
                        if (task.isSuccessful) {
                            Log.d(TAG, "There has been an error in authentication")
                        } else {
                            val intent = Intent(this@Login, MainActivity::class.java)
                            startActivity(intent)
                        }
                    }
            }
            else{
                auth!!.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success")
                            val intent = Intent(this@Login, MainActivity::class.java)
                            startActivity(intent)
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.exception)
                            Toast.makeText(baseContext, "Authentication failed.",
                                Toast.LENGTH_SHORT).show()
                        }
                    }

            }
        }
    }

    override fun onStart() {
        super.onStart()
        auth!!.addAuthStateListener(firebaseAuthStateListener!!)
    }

    override fun onStop() {
        super.onStop()
        auth!!.removeAuthStateListener(firebaseAuthStateListener!!)
    }
}