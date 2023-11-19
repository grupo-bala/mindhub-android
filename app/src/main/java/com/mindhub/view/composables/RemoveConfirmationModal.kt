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
fun RemoveConfirmationModal(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
) {
    AlertDialog(
        onDismissRequest = onDismissRequest,
        confirmButton = {
            TextButton(onClick = onConfirmation) {
                Text(text = "Confirmar")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismissRequest) {
                Text(text = "Cancelar")
            }
        },
        title = { Text("Você tem certeza?") },
        text = { Text(text = "Ao remover não será mais possível recuperar este conteúdo.") }
    )
}

@Preview(showBackground = true)
@Composable
fun RemoveConfirmationModalPreview() {
    MindHubTheme {

        Surface(modifier = Modifier.fillMaxSize()) {
            RemoveConfirmationModal(
                onConfirmation = { println("removed") },
                onDismissRequest = {  }
            )
        }
    }
}
