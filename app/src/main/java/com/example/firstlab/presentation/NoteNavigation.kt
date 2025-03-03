package com.example.firstlab.presentation

import android.os.Build
import android.os.Bundle
import android.view.Window
import android.view.WindowInsets
import android.view.WindowInsetsController
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.firstlab.databinding.NoteNavActivityBinding

class NoteNavigation : AppCompatActivity() {
    private var binding: NoteNavActivityBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = NoteNavActivityBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        enableEdgeToEdge()
        hideNavigationBar(window)
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }


    fun hideNavigationBar(window: Window) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.let { controller ->
                controller.hide(
                    WindowInsets.Type.navigationBars()
                )
                controller.systemBarsBehavior =
                    WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            }
        }
    }
}