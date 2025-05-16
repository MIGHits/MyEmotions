package com.example.firstlab.domain.usecase

import com.example.firstlab.domain.repository.FirebaseAuthRepository
import com.google.firebase.auth.FirebaseUser

class LoginUseCase(private val repository: FirebaseAuthRepository) {
    suspend operator fun invoke(tokenId: String): FirebaseUser {
        return repository.signInWithGoogle(tokenId)
    }
}