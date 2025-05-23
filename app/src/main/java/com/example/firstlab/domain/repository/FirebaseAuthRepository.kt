package com.example.firstlab.domain.repository

import com.google.firebase.auth.FirebaseUser

interface FirebaseAuthRepository {
    suspend fun signInWithGoogle(idToken:String)
}