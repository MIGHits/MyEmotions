package com.example.firstlab.presentation.state

import com.google.firebase.auth.FirebaseUser

sealed class AuthState {
    data object Initial : AuthState()
    data class Success(val user: FirebaseUser) : AuthState()
    data object Loading : AuthState()
    data class Error(val message: String) : AuthState()
}