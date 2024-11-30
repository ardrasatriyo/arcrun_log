package com.example.arcrun_appexpfix

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.AuthUI.IdpConfig
import com.firebase.ui.auth.AuthUI.IdpConfig.EmailBuilder
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.google.firebase.auth.ActionCodeSettings
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets


        }
        val button = findViewById<Button>(R.id.button_login)
        button.setOnClickListener {
            // Memulai Sign-In dengan Google
            createSignInIntent()
        }



    }



    private val signInLauncher = registerForActivityResult(
        FirebaseAuthUIActivityResultContract(),
    ) {
            res ->
        this.onSignInResult(res)
    }

    private fun createSignInIntent(){
        val providers = arrayListOf(
            AuthUI.IdpConfig.GoogleBuilder().build()
        )

        val signInIntent = AuthUI.getInstance().createSignInIntentBuilder().setAvailableProviders(providers).build()
        signInLauncher.launch(signInIntent)
    }

    private fun onSignInResult(result: FirebaseAuthUIAuthenticationResult){
        val response = result.idpResponse
        if (result.resultCode == RESULT_OK){
            val user = FirebaseAuth.getInstance().currentUser
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
            finish() // Close the sign-in activity
        } else {
            if (response == null) {
                // User pressed back button
                Toast.makeText(this, "Sign in canceled", Toast.LENGTH_SHORT).show()
                return
            }

        }

    }



}