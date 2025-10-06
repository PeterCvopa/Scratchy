package com.cvopa.peter.scratchy

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.cvopa.peter.scratchy.ui.Activation
import com.cvopa.peter.scratchy.ui.Main
import com.cvopa.peter.scratchy.ui.Scratch
import com.cvopa.peter.scratchy.ui.activation.ActivationScreen
import com.cvopa.peter.scratchy.ui.main.MainScreen
import com.cvopa.peter.scratchy.ui.navigateToActivityScreen
import com.cvopa.peter.scratchy.ui.navigateToScratchScreen
import com.cvopa.peter.scratchy.ui.scratch.ScratchScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : androidx.appcompat.app.AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { OliveaAppContent() }
    }

    @Composable
    private fun OliveaAppContent() {
        val navController = rememberNavController()
        MaterialTheme {
            Scaffold(
                containerColor = MaterialTheme.colorScheme.background,
            ) { innerPadding ->
                NavHost(
                    modifier =
                        Modifier.fillMaxSize().padding(top = innerPadding.calculateTopPadding()),
                    navController = navController,
                    startDestination = Main,
                    contentAlignment = Alignment.TopStart,
                ) {
                    composable<Main> {
                        MainScreen(
                            onNavigateToScratch = { navController.navigateToScratchScreen() },
                            onNavigateToActivation = { navController.navigateToActivityScreen() },
                        )
                    }
                    composable<Activation> { ActivationScreen() }
                    composable<Scratch> { ScratchScreen() }
                }
            }
        }
    }
}
