package com.mindhub.view.material

import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mindhub.view.composables.post.PostView
import com.mindhub.viewmodel.material.GetMaterialViewModel
import com.ramcosta.composedestinations.annotation.DeepLink
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator


@Destination(
    route = "material",
    deepLinks = [
        DeepLink(
            uriPattern = "https://mindhub.netlify.app/material/{materialId}",
            action = Intent.ACTION_VIEW
        )
    ]
)
@Composable
fun MaterialView(
    materialId: Int,
    navigator: DestinationsNavigator
) {
    val getViewModel: GetMaterialViewModel = viewModel()

    PostView(
        postId = materialId,
        viewModel = getViewModel,
        navigator = navigator
    )
}