package com.neontetra.filmshare

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.neontetra.filmshare.databinding.ActivityLoginBinding

private const val TAG = "LoginActivity"

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val mAuth = FirebaseAuth.getInstance()
        if (mAuth.currentUser != null){
            goToPostsActivity()
        }

        binding.btnSubmit.setOnClickListener {
            binding.btnSubmit.isEnabled = false
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()
            if (email.isBlank() || password.isBlank()) {
                Toast.makeText(
                    this,
                    "You must enter a valid username or password",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }
            mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { Task ->
                    binding.btnSubmit.isEnabled = true
                    if (Task.isSuccessful) {
                        Log.i(TAG, "Successfully logged in")
                        Toast.makeText(this, "You've successfully logged in", Toast.LENGTH_SHORT)
                            .show()
                        goToPostsActivity()
                    } else {
                        Log.e(TAG, "Incorrect password or username: ${Task.exception}")
                        Toast.makeText(this, "Authentication failed", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

    private fun goToPostsActivity() {
        Log.i(TAG, "goToPostsActivity starts")
        val intent = Intent(this, PostsActivity::class.java)
        startActivity(intent)
        finish()
    }
}