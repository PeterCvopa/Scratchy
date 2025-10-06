package com.cvopa.peter.scratchy.ui.activation

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun ActivationScreen(){
    Column {
        Text(text = "Activation screen")
    }
}

@Composable
fun ActivationScreen( f: String){
    Text(text = " activation screen")
}

@Preview
@Composable
fun Preview(){
    MaterialTheme {
        ActivationScreen("f")
    }
}




