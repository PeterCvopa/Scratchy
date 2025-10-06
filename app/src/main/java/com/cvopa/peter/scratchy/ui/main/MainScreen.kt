package com.cvopa.peter.scratchy.ui.main

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun MainScreen(onNavigateToScratch: () -> Unit = {}, onNavigateToActivation: () -> Unit = {}) {

    val viewModel: MainViewModel = hiltViewModel()
    val state by viewModel.state.collectAsStateWithLifecycle()

    MainScreen(state, viewModel::handleAction)

    LaunchedEffect(viewModel) {
        viewModel.event.collect { event ->
            when (event) {
                MainAppEvent.NavigateToActivation -> onNavigateToActivation()
                MainAppEvent.NavigateToScratch -> onNavigateToScratch()
            }
        }
    }
}

@Composable
fun MainScreen(state: MainViewScreenState, handleAction: (MainScreenActions) -> Unit) {

    Column {
        Text(text = "main screen")

        Button(onClick = { handleAction(MainScreenActions.OnButtonActivationClicked) }) {
            Text(text = "Activation")
        }
        Button(onClick = { handleAction(MainScreenActions.OnButtonScratchClicked) }) {
            Text(text = "Scratch")
        }
    }

}

@Preview
@Composable
fun Preview() {
    MaterialTheme {
        MainScreen()
    }
}
