package com.mindhub.viewmodel.profile

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mindhub.common.services.ErrorParser
import com.mindhub.common.services.UserInfo
import com.mindhub.model.api.AskFakeApi
import com.mindhub.model.api.EventFakeApi
import com.mindhub.model.api.MaterialFakeApi
import com.mindhub.model.api.ProfileFakeApi
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
        if (usernameToLoad == username || username == UserInfo!!.username) {
            return
        }

        isLoading = true

        if (usernameToLoad == null) {
            username = UserInfo!!.username
            badge = UserInfo!!.currentBadge
            xp = UserInfo!!.xp

            for (expertise in UserInfo!!.expertises) {
                expertises.add(expertise)
            }

            viewModelScope.launch {
                try {
                    for (post in AskFakeApi.getUserAsks(UserInfo!!.username)) {
                        askPosts.add(post)
                    }

                    for (post in MaterialFakeApi.getUserMaterials(UserInfo!!.username)) {
                        materialPosts.add(post)
                    }

                    for (post in EventFakeApi.getUserEvents(UserInfo!!.username)) {
                        eventsPosts.add(post)
                    }
                } catch (e: Exception) {
                    /* TODO */
                }
            }
        } else {
            viewModelScope.launch {
                try {
                    val user = ProfileFakeApi.getUserInformation(usernameToLoad)

                    username = user.username
                    badge = user.currentBadge
                    xp = user.xp

                    for (post in AskFakeApi.getUserAsks(username)) {
                        askPosts.add(post)
                    }

                    for (post in MaterialFakeApi.getUserMaterials(username)) {
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