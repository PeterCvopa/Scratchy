package com.cvopa.peter.scratchy.ui

import androidx.navigation.NavController
import kotlinx.serialization.Serializable

@Serializable object Main

@Serializable object Activation

@Serializable object Scratch

fun NavController.navigateToActivityScreen() {
    navigate(Activation)
}

fun NavController.navigateToScratchScreen() {
    navigate(Scratch)
}
