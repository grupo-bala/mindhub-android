package com.mindhub.view.profile

import android.graphics.Paint.Align
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Create
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.InputChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.mindhub.model.entities.Badge
import com.mindhub.model.entities.Expertise
import com.mindhub.model.entities.User
import com.mindhub.services.UserInfo
import com.mindhub.ui.theme.MindHubTheme
import com.mindhub.view.composables.Suspended
import com.mindhub.view.layouts.SpacedColumn
import com.mindhub.viewmodel.profile.EditProfileViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.EmptyDestinationsNavigator

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Destination
@Composable
fun EditProfile(
    navigator: DestinationsNavigator,
) {
    val viewModel: EditProfileViewModel = viewModel()
    var hasFailure by remember {
        mutableStateOf(false)
    }
    var isMenuExpanded by remember {
        mutableStateOf(false)
    }
    var isExpertisesMenuExpanded by remember {
        mutableStateOf(false)
    }

    val badgesInteraction = remember { MutableInteractionSource() }.also {
        if (it.collectIsPressedAsState().value) {
            isMenuExpanded = true
        }
    }
    val expertisesInteraction = remember { MutableInteractionSource() }.also {
        if (it.collectIsPressedAsState().value) {
            isExpertisesMenuExpanded = true
        }
    }

    viewModel.loadExpertises()
    viewModel.loadBadges()

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        SpacedColumn(
            spacing = 8,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxHeight()
                .padding(32.dp)
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data("https://picsum.photos/200")
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                modifier = Modifier
                    .clip(CircleShape)
                    .width(128.dp)
                    .height(128.dp)
            )

            Spacer(modifier = Modifier.height(32.dp))

            OutlinedTextField(
                value = viewModel.name,
                label = { Text(text = "Nome") },
                placeholder = { Text(text = "Digite o seu nome") },
                onValueChange = { viewModel.name = it },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = viewModel.email,
                label = { Text(text = "Email") },
                placeholder = { Text(text = "Digite o seu email") },
                onValueChange = { viewModel.email = it },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            Column(modifier = Modifier
                .fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    OutlinedTextField(
                        value = viewModel.badge,
                        readOnly = true,
                        label = { Text(text = "Conquista") },
                        onValueChange = {},
                        trailingIcon = { Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = null) },
                        interactionSource = badgesInteraction,
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                DropdownMenu(expanded = isMenuExpanded, onDismissRequest = { isMenuExpanded = false }) {
                    for (badge in viewModel.badges) {
                        DropdownMenuItem(text = { Text(badge) }, onClick = {
                            viewModel.badge = badge
                            isMenuExpanded = false
                        })
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = viewModel.selectedExpertises.fold("") { acc, curr ->
                    if (acc.isEmpty()) {
                        curr.title
                    } else {
                        "$acc, ${curr.title}"
                    }
                },
                readOnly = true,
                label = { Text(text = "Expertises") },
                onValueChange = {},
                trailingIcon = { Icon(imageVector = Icons.Default.Create, contentDescription = null) },
                interactionSource = expertisesInteraction,
                modifier = Modifier.fillMaxWidth()
            )

            if (isExpertisesMenuExpanded) {
                ModalBottomSheet(
                    modifier = Modifier
                        .fillMaxWidth()
                        .defaultMinSize(minHeight = 254.dp),
                    onDismissRequest = { isExpertisesMenuExpanded = false }
                ) {
                    Suspended(
                        isLoading = viewModel.expertises.size < 1,
                        loadingSize = 64.dp,
                        strokeWidth = 4.dp,
                        color = MaterialTheme.colorScheme.onBackground
                    ) {
                        FlowRow(
                            horizontalArrangement = Arrangement
                                .spacedBy(
                                    8.dp,
                                    alignment = Alignment.CenterHorizontally
                                ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(
                                    horizontal = 32.dp,
                                    vertical = 32.dp
                                ),
                        ) {
                            viewModel.expertises.forEach {
                                InputChip(
                                    selected = viewModel.isSelected(it.title),
                                    onClick = { viewModel.toggleExpertise(it) },
                                    label = { Text(text = it.title) },
                                )
                            }
                        }
                    }
                }
            }

            if (!hasFailure) {
                Spacer(modifier = Modifier.height(32.dp))
            } else {
                Text(
                    text = "Algo de errado aconteceu!",
                    color = MaterialTheme.colorScheme.error,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }

            OutlinedButton(
                onClick = {
                    viewModel.update(
                        onSuccess = {
                            UserInfo!!.email = viewModel.email
                            UserInfo!!.name = viewModel.name
                            UserInfo!!.currentBadge = viewModel.badge
                            UserInfo!!.expertises = viewModel.selectedExpertises

                            navigator.popBackStack()
                        },
                        onFailure = {
                            hasFailure = true
                        }
                    )
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Confirmar")
            }

            OutlinedButton(
                onClick = { navigator.popBackStack() },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Cancelar")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun EditProfilePreview() {
    UserInfo = User(
        name = "User",
        username = "username",
        email = "user@gmail.com",
        xp = 727,
        currentBadge ="Aprendiz",
        expertises = listOf(Expertise("Matemática"), Expertise("Geografia"), Expertise("Química")),
        token = ""
    )

    MindHubTheme {
        EditProfile(
            EmptyDestinationsNavigator
        )
    }
}