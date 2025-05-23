package com.example.firstlab.presentation.screen

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import androidx.core.bundle.Bundle
import com.google.firebase.auth.FirebaseAuth

@SuppressLint("CustomSplashScreen")
class LaunchActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val auth = FirebaseAuth.getInstance()

        val destination = if (auth.currentUser != null) {
            Intent(this, NavigationActivity::class.java)
        } else {
            Intent(this, AuthActivity::class.java)
        }

        startActivity(destination)
        finish()
    }
}