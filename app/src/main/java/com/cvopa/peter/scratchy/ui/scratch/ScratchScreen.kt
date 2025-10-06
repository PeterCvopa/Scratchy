package com.cvopa.peter.scratchy.ui.scratch

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun ScratchScreen() {
    Column {
        Text(text = "scratch screen")
    }
}


@Preview
@Composable
fun Preview(){
    MaterialTheme {
        ScratchScreen()
    }
}
