package com.mindhub.view.material

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.mindhub.common.services.UserInfo
import com.mindhub.model.entities.Badge
import com.mindhub.model.entities.Expertise
import com.mindhub.model.entities.Material
import com.mindhub.model.entities.User
import com.mindhub.view.composables.post.PostUpdate
import com.mindhub.viewmodel.material.MaterialViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.EmptyDestinationsNavigator
import java.time.LocalDateTime

@Destination
@Composable
fun MaterialUpdate(
    navigator: DestinationsNavigator,
    material: Material
) {
    val viewModel = MaterialViewModel()
    viewModel.title = material.title
    viewModel.content = material.content
    viewModel.expertise = material.expertise

    PostUpdate(
        navigator = navigator,
        viewModel = viewModel,
        postId = material.id,
        onSuccess = { navigator.popBackStack() },
    )
}

@Preview(showBackground = true)
@Composable
fun UpdateMaterialPreview() {
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

    val material = Material(
        id = 1,
        title = "Soma de raízes",
        content = "Como somar duas raízes quadradas",
        expertise = Expertise("Matemática"),
        user = UserInfo!!,
        postDate = LocalDateTime.now(),
        userScore = 0,
        score = 0
    )

    MaterialTheme {
        MaterialUpdate(
            navigator = EmptyDestinationsNavigator,
            material = material
        )
    }
}