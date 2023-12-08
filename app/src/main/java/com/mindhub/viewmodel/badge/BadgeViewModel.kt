package com.mindhub.viewmodel.badge

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.mindhub.model.entities.Badge
import com.mindhub.common.services.CurrentUser

class BadgeViewModel : ViewModel() {
    var selectedBadge by mutableStateOf(CurrentUser.user!!.currentBadge)
    var unlockedBadges = mutableStateListOf<Badge>().also {
        it.addAll(CurrentUser.user!!.badges)
    }
}