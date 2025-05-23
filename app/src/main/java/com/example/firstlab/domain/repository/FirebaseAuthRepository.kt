package com.example.firstlab.domain.repository

interface FirebaseAuthRepository {
    suspend fun signInWithGoogle(idToken: String): Boolean
    suspend fun logout()
}