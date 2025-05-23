package com.example.firstlab.presentation.screen

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.credentials.GetCredentialRequest
import androidx.credentials.exceptions.GetCredentialException
import androidx.lifecycle.lifecycleScope
import com.example.firstlab.App.Companion.credentialManager
import com.example.firstlab.R
import com.example.firstlab.databinding.ActivityMainBinding
import com.example.firstlab.presentation.state.AuthState
import com.example.firstlab.presentation.viewModel.AuthViewModel
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class AuthActivity : AppCompatActivity() {
    private var binding: ActivityMainBinding? = null
    private val viewModel by viewModel<AuthViewModel>()
    private lateinit var auth: FirebaseAuth
    private var biometricPrompt: BiometricPrompt? = null
    private lateinit var intent: Intent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        intent = Intent(this, NavigationActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        binding?.greeting?.let {
            ViewCompat.setOnApplyWindowInsetsListener(it) { v, insets ->
                val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
                insets
            }
        }

        auth = Firebase.auth
        binding?.entranceButton?.setOnClickListener {
            launchCredentialManager()
        }
        lifecycleScope.launch {
            viewModel.authState.collect {
                when (it) {
                    is AuthState.Initial -> {}
                    is AuthState.Loading -> {}
                    is AuthState.Success -> {
                        if (it.isBiometricAuthEnabled) {
                            initializeBiometric()
                        } else {
                            startActivity(intent)
                        }
                    }

                    is AuthState.Error -> {}
                }
            }
        }
    }


    private fun launchCredentialManager() {
        val googleIdOption = GetGoogleIdOption.Builder()

            .setServerClientId(ContextCompat.getString(this@AuthActivity, R.string.web_client_id))
            .setFilterByAuthorizedAccounts(false)
            .build()

        val request = GetCredentialRequest.Builder()
            .addCredentialOption(googleIdOption)
            .build()

        lifecycleScope.launch {
            try {
                val result = credentialManager.getCredential(
                    context = this@AuthActivity,
                    request = request
                )
                viewModel.signInWithGoogleCredential(result.credential)
            } catch (e: GetCredentialException) {
                Log.e(TAG, "Couldn't retrieve user's credentials: ${e.localizedMessage}")
            }
        }
    }

    private fun initializeBiometric() {
        biometricPrompt = BiometricPrompt(
            this@AuthActivity,
            ContextCompat.getMainExecutor(this),
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    result.cryptoObject
                    startActivity(intent)
                }

                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    Toast.makeText(this@AuthActivity, getString(R.string.retry), Toast.LENGTH_SHORT)
                        .show()
                }

                override fun onAuthenticationFailed() {
                    Toast.makeText(
                        this@AuthActivity,
                        getString(R.string.bio_authentication_failed), Toast.LENGTH_SHORT
                    ).show()
                }
            }
        )
        biometricPrompt?.authenticate(createBiometricPromptInfo())
    }

    private fun createBiometricPromptInfo(): BiometricPrompt.PromptInfo {
        return BiometricPrompt.PromptInfo.Builder()
            .setTitle(getString(R.string.authorization))
            .setNegativeButtonText(getString(R.string.cancel))
            .build()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}
