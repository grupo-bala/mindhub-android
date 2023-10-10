package com.mindhub.viewmodel.badge

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mindhub.model.api.BadgeFakeApi
import com.mindhub.model.entities.Badge
import com.mindhub.services.UserInfo
import kotlinx.coroutines.launch

class BadgeViewModel() : ViewModel() {
    var selectedBadge by mutableStateOf(UserInfo!!.currentBadge)
    var unlockedBadges = mutableStateListOf<Badge>()

    private var isBadgesLoaded = false

    fun loadBadges() {
        if (!isBadgesLoaded) {
            viewModelScope.launch {
                try {
                    unlockedBadges.addAll(BadgeFakeApi.getUnlockedBadges(UserInfo!!.xp))
                } catch (_: Exception) {}
            }

            isBadgesLoaded = true
        }
    }

    fun selectBadge(badge: Badge) {
        if (!unlockedBadges.contains(badge)) {
            throw Exception()
        }

        selectedBadge = badge
    }
}