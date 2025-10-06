package com.cvopa.peter.scratchy.common

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Chip
import androidx.compose.material.ChipDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.cvopa.peter.scratchy.ui.main.ScratchStateUI
import com.valentinilk.shimmer.shimmer

@Composable
fun ScratchCard(
    modifier: Modifier = Modifier,
    cardState: ScratchStateUI,
    onClick: () -> Unit = {}
) {
    Box(modifier = modifier) {
        when (cardState) {
            ScratchStateUI.UNSCRATCHED -> UnscratchedCard(onClicked = onClick)
            ScratchStateUI.SCRATCHING -> ScratchingCard()
            is ScratchStateUI.SCRATCHED -> ScratchedCard(code = cardState.code)
            ScratchStateUI.ACTIVATED -> {
                UnscratchedCard(onClicked = onClick, active = true)
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun UnscratchedCard(onClicked: (() -> Unit)? = null, active: Boolean = false) {
    Column {
        Box(modifier = Modifier, contentAlignment = Alignment.Center) {
            Card(
                modifier =
                    Modifier.size(300.dp)
                        .background(
                            MaterialTheme.colorScheme.onPrimaryFixedVariant,
                            shape = MaterialTheme.shapes.large
                        )
                        .aspectRatio(1.586f)
                        .clickable(
                            enabled = onClicked != null,
                            onClick = { onClicked?.let { onClicked() } }
                        ),
                elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
            ) {
                Box(
                    modifier = Modifier.fillMaxHeight(),
                    contentAlignment = Alignment.Center,
                ) {
                    val color1 = MaterialTheme.colorScheme.onPrimaryFixedVariant
                    val color2 = MaterialTheme.colorScheme.error
                    val color3 =
                        if (active) Color.Green else MaterialTheme.colorScheme.errorContainer
                    Canvas(modifier = Modifier.fillMaxSize().background(color3)) {
                        drawCircle(
                            color = color2,
                            center = Offset(x = size.width * 0.3f, y = size.height * 0.6f),
                            radius = size.minDimension * 0.85f
                        )
                        drawCircle(
                            color = color1,
                            center = Offset(x = size.width * 0.01f, y = size.height * 0.3f),
                            radius = size.minDimension * 0.75f
                        )
                    }

                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        if (active) {
                            Chip(
                                onClick = {},
                                colors = ChipDefaults.chipColors(backgroundColor = Color.Green)
                            ) {
                                Text(
                                    modifier = Modifier,
                                    textAlign = TextAlign.Center,
                                    text = "Active",
                                )
                            }
                        }
                        Text(
                            color = Color.White,
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center,
                            text = "SCRACHY CARD"
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun ScratchingCard() {
    Column {
        Box(modifier = Modifier, contentAlignment = Alignment.Center) {
            Card(
                modifier =
                    Modifier.size(300.dp)
                        .background(
                            Color.Gray.copy(alpha = 0.5f),
                            shape = MaterialTheme.shapes.large
                        )
                        .shimmer()
                        .aspectRatio(1.586f),
                elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
            ) {
                Box(
                    modifier = Modifier.fillMaxHeight(),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        text = "SCRACHY CARD",
                        color = Color.Black
                    )
                }
            }
        }
    }
}

@Composable
private fun ScratchedCard(code: String) {
    Column {
        Box(modifier = Modifier, contentAlignment = Alignment.Center) {
            Card(
                modifier =
                    Modifier.size(300.dp)
                        .background(Color.Black, shape = MaterialTheme.shapes.large)
                        .aspectRatio(1.586f),
                elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
            ) {
                Box(
                    modifier = Modifier.fillMaxHeight().padding(horizontal = 12.dp),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        text = code,
                        color = Color.Black,
                    )
                }
            }
        }
    }
}

@ScratchyPreviewsComponents
@Composable
fun Preview() {
    MaterialTheme {
        Column() {
            ScratchCard(cardState = ScratchStateUI.ACTIVATED)
            ScratchCard(cardState = ScratchStateUI.SCRATCHED("324e423 234  fdsf sfsdfsf 233 232 2"))
            ScratchCard(cardState = ScratchStateUI.UNSCRATCHED)
            ScratchCard(cardState = ScratchStateUI.SCRATCHING)
        }
    }
}
