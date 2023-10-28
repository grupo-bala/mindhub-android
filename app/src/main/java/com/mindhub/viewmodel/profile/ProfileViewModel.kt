package com.mindhub.viewmodel.profile

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mindhub.common.services.ErrorParser
import com.mindhub.common.services.UserInfo
import com.mindhub.model.api.ProfileFakeApi
import com.mindhub.model.entities.Badge
import com.mindhub.model.entities.Expertise
import kotlinx.coroutines.launch

class ProfileViewModel(): ViewModel() {
    var username by mutableStateOf("carregando...")
    var xp by mutableIntStateOf(0)
    var badge by mutableStateOf<Badge>(Badge(""))
    var expertises = mutableStateListOf<Expertise>()
    var isLoading by mutableStateOf(true)
    var feedback by mutableStateOf("")

    fun loadProfile(usernameToLoad: String?) {
        if (usernameToLoad == username || username == UserInfo!!.username) {
            return
        }

        if (usernameToLoad == null) {
            username = UserInfo!!.username
            badge = UserInfo!!.currentBadge
            xp = UserInfo!!.xp

            for (expertise in UserInfo!!.expertises) {
                expertises.add(expertise)
            }
        } else {
            viewModelScope.launch {
                try {
                    val user = ProfileFakeApi.getUserInformation(usernameToLoad)

                    username = user.username
                    badge = user.currentBadge
                    xp = user.xp

                    for (expertise in user.expertises) {
                        expertises.add(expertise)
                    }
                } catch (e: Exception) {
                    feedback = ErrorParser.from(e.message)
                }
            }
        }

        isLoading = false
    }
}