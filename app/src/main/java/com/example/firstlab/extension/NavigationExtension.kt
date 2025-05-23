package com.example.firstlab.extension

import androidx.navigation.NavController
import androidx.navigation.NavOptions

fun NavController.navigateWithClearBackStack(destinationId: Int) {
    val options = NavOptions.Builder()
        .setPopUpTo(this.graph.startDestinationId, inclusive = false)
        .build()
    this.navigate(destinationId, null, options)
}