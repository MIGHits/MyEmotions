package com.example.firstlab.presentation.viewModel

import android.content.ContentValues.TAG
import android.util.Log
import androidx.core.content.ContextCompat.getString
import androidx.credentials.Credential
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.exceptions.GetCredentialException
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import com.example.firstlab.R
import com.example.firstlab.domain.usecase.LoginUseCase
import com.example.firstlab.presentation.state.AuthState
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AuthViewModel(private val loginUseCase: LoginUseCase) : ViewModel() {
    private val _authState = MutableStateFlow<AuthState>(AuthState.Initial)
    val authState: StateFlow<AuthState> get() = _authState

    fun signInWithGoogleCredential(credential: Credential) {
        viewModelScope.launch {
            try {
                val idToken = extractIdToken(credential)
                val user = loginUseCase(idToken)
                _authState.update { AuthState.Success(user) }
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