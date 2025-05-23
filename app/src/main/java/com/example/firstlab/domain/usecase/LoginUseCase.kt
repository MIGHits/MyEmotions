package com.example.firstlab.domain.usecase

import com.example.firstlab.domain.repository.FirebaseAuthRepository

class LoginUseCase(private val repository: FirebaseAuthRepository) {
    suspend operator fun invoke(tokenId: String): Boolean {
        return repository.signInWithGoogle(tokenId)
    }
}