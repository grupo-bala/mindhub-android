package com.mindhub.view.composables.post

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mindhub.model.entities.Badge
import com.mindhub.model.entities.Expertise
import com.mindhub.model.entities.User
import com.mindhub.common.services.UserInfo
import com.mindhub.ui.theme.MindHubTheme
import com.mindhub.view.layouts.SpacedColumn
import com.mindhub.viewmodel.event.EventViewModel
import com.mindhub.viewmodel.expertise.ExpertiseViewModel
import com.mindhub.viewmodel.material.MaterialViewModel
import com.mindhub.viewmodel.post.PostViewModelInterface
import com.ramcosta.composedestinations.annotation.Destination
import io.ktor.util.reflect.instanceOf

@OptIn(ExperimentalMaterial3Api::class)
@Destination
@Composable
fun PostInputs(
    viewModel: PostViewModelInterface,
    extraContent: @Composable () -> Unit = {},
) {
    var isMenuExpanded by remember {
        mutableStateOf(false)
    }

    val expertiseInteraction = remember { MutableInteractionSource() }.also {
        if (it.collectIsPressedAsState().value) {
            isMenuExpanded = true
        }
    }

    val expertiseViewModel = ExpertiseViewModel()

    expertiseViewModel.loadExpertises()

    Surface {
        SpacedColumn(
            spacing = 8,
            verticalAlignment = Alignment.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .padding(bottom = 16.dp)
        ) {
            OutlinedTextField(
                value = viewModel.title ?: "",
                label = { Text(text = "Título*") },
                placeholder = { Text(text = "Digite o título") },
                onValueChange = { viewModel.title = it },
                modifier = Modifier.fillMaxWidth()
            )

            if (!viewModel.instanceOf(EventViewModel::class)) {
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    ExposedDropdownMenuBox(
                        expanded = isMenuExpanded,
                        onExpandedChange = { }
                    ) {
                        OutlinedTextField(
                            value = viewModel.expertise!!.title ?: "",
                            readOnly = true,
                            label = { Text(text = "Categoria*") },
                            onValueChange = {},
                            trailingIcon = {
                                Icon(
                                    imageVector = Icons.Default.ArrowDropDown,
                                    contentDescription = null
                                )
                            },
                            interactionSource = expertiseInteraction,
                            modifier = Modifier
                                .fillMaxWidth()
                                .menuAnchor()
                        )

                        DropdownMenu(
                            expanded = isMenuExpanded,
                            onDismissRequest = { isMenuExpanded = false },
                            modifier = Modifier
                                .exposedDropdownSize(matchTextFieldWidth = true)
                                .height(280.dp)
                        ) {
                            for (expertise in expertiseViewModel.expertises) {
                                DropdownMenuItem(
                                    text = { Text(expertise.title) },
                                    onClick = {
                                        viewModel.expertise = expertise
                                        isMenuExpanded = false
                                    }
                                )
                            }
                        }
                    }
                }
            }

            OutlinedTextField(
                value = viewModel.content ?: "",
                label = { Text(text = "Descrição*") },
                placeholder = { Text(text = "Digite a descrição") },
                onValueChange = { viewModel.content = it },
                modifier = Modifier
                    .height(450.dp)
                    .fillMaxWidth()
            )

            extraContent()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CreatePostPreview() {
    MindHubTheme {
        UserInfo = User(
            name = "User",
            username = "username",
            email = "user@gmail.com",
            xp = 727,
            currentBadge = Badge("Aprendiz", 0),
            badges = listOf(),
            expertises = listOf(Expertise("Matemática"), Expertise("Geografia"), Expertise("Química")),
            token = ""
        )

        PostInputs(
            viewModel = MaterialViewModel(),
        )
    }
}