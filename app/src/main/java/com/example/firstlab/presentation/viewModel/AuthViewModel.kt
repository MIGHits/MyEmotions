package com.example.firstlab.presentation.viewModel

import androidx.credentials.Credential
import androidx.credentials.CustomCredential
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.firstlab.domain.usecase.LoginUseCase
import com.example.firstlab.presentation.state.AuthState
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AuthViewModel(private val loginUseCase: LoginUseCase) : ViewModel() {
    private val _authState = MutableStateFlow<AuthState>(AuthState.Initial)
    val authState: StateFlow<AuthState> get() = _authState

    fun signInWithGoogleCredential(credential: Credential) {
        viewModelScope.launch {
            _authState.update { AuthState.Loading }
            try {
                val idToken = extractIdToken(credential)
                val isBiometricAuthEnabled = loginUseCase(idToken)
                _authState.update { AuthState.Success(isBiometricAuthEnabled) }
            } catch (e: Exception) {
                _authState.update { AuthState.Error(e.message ?: "Unknown error") }
            }
        }
    }

    private fun extractIdToken(credential: Credential): String {
        if (credential is CustomCredential &&
            credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL
        ) {
            val googleCredential = GoogleIdTokenCredential.createFrom(credential.data)
            return googleCredential.idToken
        } else {
            throw IllegalArgumentException("Invalid credential type")
        }
    }
}