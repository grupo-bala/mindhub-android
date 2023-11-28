package com.mindhub.view.profile

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Create
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.InputChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.mindhub.model.entities.Badge
import com.mindhub.model.entities.Expertise
import com.mindhub.model.entities.User
import com.mindhub.common.services.CurrentUser
import com.mindhub.ui.theme.MindHubTheme
import com.mindhub.view.composables.Suspended
import com.mindhub.view.layouts.AppScaffold
import com.mindhub.view.layouts.SpacedColumn
import com.mindhub.view.layouts.Views
import com.mindhub.viewmodel.badge.BadgeViewModel
import com.mindhub.viewmodel.expertise.ExpertiseViewModel
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
    val editProfileViewModel: EditProfileViewModel = viewModel()
    val expertiseViewModel: ExpertiseViewModel = viewModel()
    val badgeViewModel: BadgeViewModel = viewModel()

    var isBadgesMenuExpanded by remember {
        mutableStateOf(false)
    }
    var isExpertisesMenuExpanded by remember {
        mutableStateOf(false)
    }

    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = {
            editProfileViewModel.photo = it
        }
    )

    val badgesMenuInteraction = remember { MutableInteractionSource() }.also {
        if (it.collectIsPressedAsState().value) {
            isBadgesMenuExpanded = true
        }
    }
    val expertisesMenuInteraction = remember { MutableInteractionSource() }.also {
        if (it.collectIsPressedAsState().value) {
            isExpertisesMenuExpanded = true
        }
    }

    expertiseViewModel.loadExpertises()
    badgeViewModel.loadBadges()

    AppScaffold(
        currentView = Views.USER,
        navigator = navigator,
        bottomAppBar = {}
    ) {
        SpacedColumn(
            spacing = 8,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxHeight()
                .padding(32.dp)
        ) {
            Box {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(
                            if (editProfileViewModel.photo != null) {
                                editProfileViewModel.photo
                            } else if (CurrentUser.user!!.profilePicture == null) {
                                "https://picsum.photos/200"
                            } else {
                                CurrentUser.user!!.profilePicture
                            }
                        )
                        .crossfade(true)
                        .build(),
                    contentDescription = null,
                    modifier = Modifier
                        .clip(CircleShape)
                        .width(128.dp)
                        .height(128.dp)
                        .align(alignment = Alignment.Center)
                )

                IconButton(
                    colors = IconButtonDefaults
                        .iconButtonColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer
                        ),
                    onClick = {
                        photoPickerLauncher.launch(
                            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                        )
                    },
                    modifier = Modifier
                        .align(alignment = Alignment.BottomEnd)
                        .padding(top = 96.dp)
                ) {
                    Icon(imageVector = Icons.Filled.Create, contentDescription = null)
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            OutlinedTextField(
                value = editProfileViewModel.name,
                label = { Text(text = "Nome") },
                placeholder = { Text(text = "Digite o seu nome") },
                onValueChange = { editProfileViewModel.name = it },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = editProfileViewModel.email,
                label = { Text(text = "Email") },
                placeholder = { Text(text = "Digite o seu email") },
                onValueChange = { editProfileViewModel.email = it },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            ExposedDropdownMenuBox(
                expanded = isBadgesMenuExpanded,
                onExpandedChange = { }
            ) {
                OutlinedTextField(
                    value = badgeViewModel.selectedBadge.title,
                    readOnly = true,
                    label = { Text(text = "Conquista") },
                    onValueChange = {},
                    trailingIcon = {
                        Icon(
                            imageVector = Icons.Default.ArrowDropDown,
                            contentDescription = null
                        )
                    },
                    interactionSource = badgesMenuInteraction,
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor()
                )

                DropdownMenu(
                    expanded = isBadgesMenuExpanded,
                    onDismissRequest = { isBadgesMenuExpanded = false },
                    modifier = Modifier
                        .exposedDropdownSize(matchTextFieldWidth = true)
                ) {
                    for (badge in badgeViewModel.unlockedBadges) {
                        DropdownMenuItem(
                            text = { Text(badge.title) },
                            onClick = {
                                badgeViewModel.selectedBadge = badge
                                isBadgesMenuExpanded = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = expertiseViewModel.selectedExpertises.fold("") { acc, curr ->
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
                interactionSource = expertisesMenuInteraction,
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
                        isLoading = expertiseViewModel.expertises.size < 1,
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
                            expertiseViewModel.expertises.forEach {
                                InputChip(
                                    selected = expertiseViewModel.isSelected(it),
                                    onClick = { expertiseViewModel.toggleExpertise(it) },
                                    label = { Text(text = it.title) },
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = {
                    editProfileViewModel.update(
                        badgeViewModel.selectedBadge,
                        expertiseViewModel.selectedExpertises,
                        onSuccess = {
                            navigator.popBackStack()
                        },
                        onFailure = {
                            TODO("No errors are defined in the fake api")
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
    CurrentUser.user = User(
        name = "User",
        username = "username",
        email = "user@gmail.com",
        xp = 727,
        currentBadge = Badge("Aprendiz", 0),
        badges = listOf(),
        expertises = listOf(Expertise("Matemática"), Expertise("Geografia"), Expertise("Química"))
    )

    MindHubTheme {
        EditProfile(
            EmptyDestinationsNavigator
        )
    }
}