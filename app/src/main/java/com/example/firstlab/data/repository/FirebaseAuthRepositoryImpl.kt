package com.example.firstlab.data.repository

import android.content.ContentValues.TAG
import android.util.Log
import androidx.credentials.ClearCredentialStateRequest
import androidx.credentials.exceptions.ClearCredentialException
import com.example.firstlab.App.Companion.credentialManager
import com.example.firstlab.data.dao.UserDao
import com.example.firstlab.data.model.UserDbModel
import com.example.firstlab.domain.repository.FirebaseAuthRepository
import com.google.firebase.auth.FirebaseAuth
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
    override suspend fun signInWithGoogle(idToken: String): Boolean {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        var isBiometricAuthEnabled = false
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
            exists?.let { isBiometricAuthEnabled = it.isFingerprintEnabled }
            if (exists == null) {
                val user = UserDbModel(
                    id = firebaseUser.uid,
                    username = firebaseUser.displayName ?: "No name",
                    avatar = firebaseUser.photoUrl?.toString() ?: ""
                )
                userDao.addUser(user)
            }
        }
        return isBiometricAuthEnabled
    }

    override suspend fun logout() {
        auth.signOut()

        withContext(Dispatchers.IO) {
            try {
                val clearRequest = ClearCredentialStateRequest()

                credentialManager.clearCredentialState(clearRequest)
            } catch (e: ClearCredentialException) {
                Log.e(TAG, "Couldn't clear user credentials: ${e.localizedMessage}")
            }
        }
    }
}
