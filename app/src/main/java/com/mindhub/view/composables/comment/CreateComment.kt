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
import com.mindhub.viewmodel.comment.CreateCommentViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateComment(
    isReply: Boolean,
    createCommentViewModel: CreateCommentViewModel,
    onCreate: () -> Unit,
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
                text = if (isReply) "Adicionar resposta" else "Adicionar comentário",
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = createCommentViewModel.commentText,
                label = { Text(text = "Comentário") },
                placeholder = { Text(text = "Digite seu comentário") },
                onValueChange = { createCommentViewModel.commentText = it },
                modifier = Modifier
                    .height(250.dp)
                    .fillMaxWidth()
            )

            Row {
                Button(
                    onClick = {
                        onCreate()
                    },
                    shape = RoundedCornerShape(5.dp),
                    modifier = Modifier.weight(1f),
                ) {
                    Icon(imageVector = Icons.Filled.Add, contentDescription = null)
                    Spacer(modifier = Modifier.width(2.dp))
                    Text(text = "Adicionar")
                }

                Spacer(modifier = Modifier.width(4.dp))

                if (createCommentViewModel.commentText.isNotEmpty()) {
                    Button(
                        onClick = { createCommentViewModel.clear() },
                        shape = RoundedCornerShape(5.dp)
                    ) {
                        Icon(imageVector = Icons.Filled.Delete, contentDescription = null)
                    }
                }
            }
        }
    }
}