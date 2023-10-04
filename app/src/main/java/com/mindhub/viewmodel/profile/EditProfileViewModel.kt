package com.mindhub.viewmodel.profile

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.mindhub.model.entities.Badge
import com.mindhub.services.UserInfo

class EditProfileViewModel() : ViewModel() {
    var name by mutableStateOf(UserInfo!!.name)
    var username by mutableStateOf(UserInfo!!.username)
    var email by mutableStateOf(UserInfo!!.email)
    var badge by mutableStateOf(UserInfo!!.currentBadge)
    var badges = mutableListOf<Badge>()

    private var isAchievementsLoaded = false
    fun loadAchievements() {
        if (!isAchievementsLoaded) {
            // TODO: load achievements based on the API

            badges.addAll(listOf(Badge("A"), Badge("B"), Badge("C")))

            isAchievementsLoaded = true
        }
    }
}
