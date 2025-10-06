package com.cvopa.peter.scratchy.ui.scratch

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.cvopa.peter.scratchy.common.ScratchCard
import com.cvopa.peter.scratchy.common.ScratchyPreviews
import com.cvopa.peter.scratchy.ui.main.ScratchStateUI

@Composable
fun ScratchScreen() {

    val viewModel: ScratchScreenViewModel = hiltViewModel()
    val state by viewModel.state.collectAsStateWithLifecycle()

    ScratchScreen(state, viewModel::handleAction)
}

@Composable
fun ScratchScreen(state: ScratchScreenState, onAction: (ScratchScreenActions) -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ScratchCard(
            cardState = state.cardState,
            onClick = { onAction(ScratchScreenActions.OnScratchClicked) }
        )
        if (state.errorState) {
            Row { Text(text = "Error") }
        }
    }
}

@ScratchyPreviews
@Composable
fun Preview() {
    MaterialTheme {
        ScratchScreen(state = ScratchScreenState(cardState = ScratchStateUI.SCRATCHING)) {}
    }
}
