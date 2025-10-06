package com.cvopa.peter.scratchy.ui.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.cvopa.peter.scratchy.common.ScratchCard

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
    Column(
        modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ScratchCard(cardState = state.cardState) {}

        Button(onClick = { handleAction(MainScreenActions.OnButtonActivationClicked) }) {
            Text(text = "Activation")
        }
        Spacer(modifier = Modifier.size(12.dp))
        Button(onClick = { handleAction(MainScreenActions.OnButtonScratchClicked) }) {
            Text(text = "Scratch")
        }
    }
}

@Preview
@Composable
fun Preview() {
    MaterialTheme {
        MainScreen(state = MainViewScreenState(cardState = ScratchStateUI.ACTIVATED)) {}
    }
}
