package com.example.firstlab.presentation.state

sealed class AuthState {
    data object Initial : AuthState()
    data class Success(val isBiometricAuthEnabled: Boolean) : AuthState()
    data object Loading : AuthState()
    data class Error(val message: String) : AuthState()
}