package com.mindhub.view.material

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mindhub.view.composables.feed.FeedView
import com.mindhub.view.destinations.MaterialCreateDestination
import com.mindhub.view.destinations.MaterialViewDestination
import com.mindhub.view.layouts.Views
import com.mindhub.viewmodel.material.FeedMaterialViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination
@Composable
fun MaterialFeed(
    navigator: DestinationsNavigator
) {
    val feed: FeedMaterialViewModel = viewModel()

    FeedView(
        navigator = navigator,
        viewModel = feed,
        currentView = Views.MATERIAL,
        onClickItem = { navigator.navigate(MaterialViewDestination(it.id)) },
        onClickAdd = { navigator.navigate(MaterialCreateDestination(null)) }
    )
}