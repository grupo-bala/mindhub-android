package com.mindhub.view.composables.comment

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.mindhub.view.layouts.SpacedColumn
import com.mindhub.viewmodel.comment.HandleCommentCreationViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HandleComment(
    isUpdate: Boolean,
    handleCommentViewModel: HandleCommentCreationViewModel,
    onSuccess: () -> Unit,
    onDismissRequest: () -> Unit
) {
    ModalBottomSheet(
        modifier = Modifier
            .fillMaxWidth()
            .defaultMinSize(minHeight = 254.dp),
        onDismissRequest = { onDismissRequest() }
    ) {
        SpacedColumn(
            spacing = 8,
            verticalAlignment = Alignment.CenterVertically,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(16.dp),
        ) {
            Text(
                text = if (isUpdate) "Atualizar comentário" else "Adicionar comentário",
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = handleCommentViewModel.commentText,
                label = { Text(text = "Comentário") },
                placeholder = { Text(text = "Digite seu comentário") },
                onValueChange = { handleCommentViewModel.commentText = it },
                modifier = Modifier
                    .height(250.dp)
                    .fillMaxWidth()
            )

            Row {
                Button(
                    onClick = {
                        onSuccess()
                    },
                    shape = RoundedCornerShape(5.dp),
                    modifier = Modifier.weight(1f),
                ) {
                    Icon(imageVector = if (isUpdate) Icons.Filled.Create else Icons.Filled.Add, contentDescription = null)
                    Spacer(modifier = Modifier.width(2.dp))
                    Text(text = if (isUpdate) "Atualizar" else "Adicionar")
                }

                Spacer(modifier = Modifier.width(4.dp))

                if (handleCommentViewModel.commentText.isNotEmpty()) {
                    Button(
                        onClick = { handleCommentViewModel.clear() },
                        shape = RoundedCornerShape(5.dp)
                    ) {
                        Icon(imageVector = Icons.Filled.Delete, contentDescription = null)
                    }
                }
            }
        }
    }
}