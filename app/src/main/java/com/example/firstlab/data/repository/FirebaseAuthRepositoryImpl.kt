package com.example.firstlab.data.repository

import com.example.firstlab.data.dao.UserDao
import com.example.firstlab.data.model.UserDbModel
import com.example.firstlab.domain.repository.FirebaseAuthRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import kotlin.coroutines.resumeWithException

class FirebaseAuthRepositoryImpl(
    private val auth: FirebaseAuth,
    private val userDao: UserDao
) : FirebaseAuthRepository {

    @OptIn(ExperimentalCoroutinesApi::class)
    override suspend fun signInWithGoogle(idToken: String): FirebaseUser {
        val credential = GoogleAuthProvider.getCredential(idToken, null)

        val firebaseUser = suspendCancellableCoroutine { cont ->
            auth.signInWithCredential(credential).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    auth.currentUser?.let { cont.resume(it, null) }
                        ?: cont.resumeWithException(Exception("User is null"))
                } else {
                    cont.resumeWithException(task.exception ?: Exception("Auth error"))
                }
            }
        }

        withContext(Dispatchers.IO) {
            val exists = userDao.getUserById(firebaseUser.uid)
            if (exists == null) {
                val user = UserDbModel(
                    id = firebaseUser.uid,
                    username = firebaseUser.displayName ?: "No name",
                    avatar = firebaseUser.photoUrl?.toString() ?: ""
                )
                userDao.addUser(user)
            }
        }
        return firebaseUser
    }
}
