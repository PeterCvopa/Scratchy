package com.cvopa.peter.scratchy.ui.activation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Chip
import androidx.compose.material.ChipDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.IconButton
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.cvopa.peter.scratchy.R
import com.cvopa.peter.scratchy.common.ErrorState
import com.cvopa.peter.scratchy.ui.main.ScratchStateUI

@Composable
fun ActivationScreen() {

    val viewModel: ActivationScreenViewModel = hiltViewModel()
    val state by viewModel.state.collectAsStateWithLifecycle()

    ActivationScreen(state, viewModel::handleAction)
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ActivationScreen(
    state: ActivationScreenState,
    handleAction: (ActivationScreenActions) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)
    ) {
        Spacer(modifier = Modifier.weight(1f))

        if (state.cardState is ScratchStateUI.SCRATCHED) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                text = "Activation Code",
            )
            Text(
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                text = state.cardState.code,
            )
        }

        if (state.cardState is ScratchStateUI.ACTIVATED) {
            Chip(onClick = {}, colors = ChipDefaults.chipColors(backgroundColor = Color.Green)) {
                Text(
                    modifier = Modifier,
                    textAlign = TextAlign.Center,
                    text = "Active",
                )
            }
        }

        Button(
            modifier = Modifier,
            onClick = { handleAction(ActivationScreenActions.OnActivation) }
        ) {
            Text(text = "Activation")
        }

        Spacer(modifier = Modifier.weight(1f))

        AnimatedVisibility(visible = state.error?.message != null) {
            state.error?.message?.let { msg ->
                Box(
                    modifier =
                        Modifier.padding(14.dp)
                            .fillMaxWidth()
                            .background(
                                MaterialTheme.colorScheme.errorContainer,
                                shape = RoundedCornerShape(12.dp)
                            ),
                    contentAlignment = Alignment.Center,
                ) {
                    IconButton(
                        onClick = { handleAction(ActivationScreenActions.OnDismissError) },
                        modifier = Modifier.padding(12.dp).align(Alignment.BottomEnd),
                    ) {
                        Icon(
                            painter =
                                painterResource(android.R.drawable.ic_menu_close_clear_cancel),
                            contentDescription = null
                        )
                    }
                    Row(modifier = Modifier.padding(12.dp)) {
                        Text(
                            modifier = Modifier.weight(1f),
                            text = msg,
                            textAlign = TextAlign.Center,
                            color = MaterialTheme.colorScheme.onErrorContainer
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun Preview() {
    MaterialTheme {
        ActivationScreen(
            state =
                ActivationScreenState(
                    cardState = ScratchStateUI.ACTIVATED,
                    error = ErrorState.NoCardCodeError
                ),
        ) {}
    }
}
