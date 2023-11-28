package com.mindhub.viewmodel.profile

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mindhub.common.services.ErrorParser
import com.mindhub.common.services.CurrentUser
import com.mindhub.model.api.AskFakeApi
import com.mindhub.model.api.EventFakeApi
import com.mindhub.model.api.MaterialApi
import com.mindhub.model.api.ProfileApi
import com.mindhub.model.entities.Badge
import com.mindhub.model.entities.Expertise
import com.mindhub.model.entities.Post
import kotlinx.coroutines.launch

class ProfileViewModel(): ViewModel() {
    var username by mutableStateOf("carregando...")
    var askPosts: MutableList<Post> = mutableStateListOf<Post>()
    var materialPosts: MutableList<Post> = mutableStateListOf<Post>()
    var eventsPosts: MutableList<Post> = mutableStateListOf<Post>()
    var xp by mutableIntStateOf(0)
    var badge by mutableStateOf<Badge>(Badge("", 0))
    var expertises = mutableStateListOf<Expertise>()
    var isLoading by mutableStateOf(true)
    var feedback by mutableStateOf("")

    fun loadProfile(usernameToLoad: String?) {
        if (usernameToLoad == username || username == CurrentUser.user!!.username) {
            return
        }

        isLoading = true

        if (usernameToLoad == null) {
            username = CurrentUser.user!!.username
            badge = CurrentUser.user!!.currentBadge
            xp = CurrentUser.user!!.xp

            for (expertise in CurrentUser.user!!.expertises) {
                expertises.add(expertise)
            }

            viewModelScope.launch {
                try {
                    for (post in AskFakeApi.getUserAsks(CurrentUser.user!!.username)) {
                        askPosts.add(post)
                    }

                    for (post in MaterialApi.getUserMaterials(CurrentUser.user!!.username)) {
                        materialPosts.add(post)
                    }

                    for (post in EventFakeApi.getUserEvents(CurrentUser.user!!.username)) {
                        eventsPosts.add(post)
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        } else {
            viewModelScope.launch {
                try {
                    val user = ProfileApi.getUserInformation(usernameToLoad)

                    username = user.username
                    badge = user.currentBadge
                    xp = user.xp

                    for (post in AskFakeApi.getUserAsks(username)) {
                        askPosts.add(post)
                    }

                    for (post in MaterialApi.getUserMaterials(username)) {
                        materialPosts.add(post)
                    }

                    for (post in EventFakeApi.getUserEvents(username)) {
                        eventsPosts.add(post)
                    }

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