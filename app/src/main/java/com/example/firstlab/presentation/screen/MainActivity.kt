package com.example.firstlab.presentation.screen

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.credentials.exceptions.GetCredentialException
import androidx.lifecycle.lifecycleScope
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

class MainActivity() : AppCompatActivity() {
    private var binding: ActivityMainBinding? = null
    private val viewModel by viewModel<AuthViewModel>()
    private lateinit var auth: FirebaseAuth
    private lateinit var credentialManager: CredentialManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        binding?.greeting?.let {
            ViewCompat.setOnApplyWindowInsetsListener(it) { v, insets ->
                val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
                insets
            }
        }
        auth = Firebase.auth
        credentialManager = CredentialManager.create(this@MainActivity)
        binding?.entranceButton?.setOnClickListener {
            launchCredentialManager()
        }
        lifecycleScope.launch {
            viewModel.authState.collect {
                when (it) {
                    is AuthState.Initial -> {}
                    is AuthState.Loading -> {}
                    is AuthState.Success -> {
                        val intent = Intent(this@MainActivity, NavigationActivity::class.java)
                        startActivity(intent)
                    }
                    is AuthState.Error -> {}
                }
            }
        }
    }


    private fun launchCredentialManager() {
        val googleIdOption = GetGoogleIdOption.Builder()

            .setServerClientId(ContextCompat.getString(this@MainActivity, R.string.web_client_id))
            .setFilterByAuthorizedAccounts(false)
            .build()

        val request = GetCredentialRequest.Builder()
            .addCredentialOption(googleIdOption)
            .build()

        lifecycleScope.launch {
            try {
                val result = credentialManager.getCredential(
                    context = this@MainActivity,
                    request = request
                )
                viewModel.signInWithGoogleCredential(result.credential)
            } catch (e: GetCredentialException) {
                Log.e(TAG, "Couldn't retrieve user's credentials: ${e.localizedMessage}")
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}
