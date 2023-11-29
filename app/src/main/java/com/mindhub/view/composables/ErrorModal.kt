package com.mindhub.view.composables

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.mindhub.ui.theme.MindHubTheme

@Composable
fun ErrorModal(
    text: String,
    onConfirmation: () -> Unit,
) {
    AlertDialog(
        onDismissRequest = {},
        confirmButton = {
            TextButton(onClick = onConfirmation) {
                Text(text = "Entendi")
            }
        },
        dismissButton = null,
        title = { Text("Algo de errado aconteceu...") },
        text = { Text(text = text) }
    )
}

@Preview(showBackground = true)
@Composable
fun ErrorModal() {
    MindHubTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            ErrorModal(
                onConfirmation = { },
                text = ""
            )
        }
    }
}
