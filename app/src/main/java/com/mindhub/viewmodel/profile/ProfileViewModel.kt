package com.mindhub.viewmodel.profile

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mindhub.common.services.ErrorParser
import com.mindhub.model.api.AskApi
import com.mindhub.model.api.EventApi
import com.mindhub.model.api.MaterialApi
import com.mindhub.model.api.ProfileApi
import com.mindhub.model.entities.Badge
import com.mindhub.model.entities.Expertise
import com.mindhub.model.entities.Post
import kotlinx.coroutines.launch

class ProfileViewModel(): ViewModel() {
    var username by mutableStateOf("carregando...")
    var name by mutableStateOf("carregando...")
    var askPosts: MutableList<Post> = mutableStateListOf<Post>()
    var materialPosts: MutableList<Post> = mutableStateListOf<Post>()
    var eventsPosts: MutableList<Post> = mutableStateListOf<Post>()
    var xp by mutableIntStateOf(0)
    var badge by mutableStateOf<Badge>(Badge("", 0))
    var expertises = mutableStateListOf<Expertise>()
    var isLoading by mutableStateOf(true)
    var feedback by mutableStateOf("")

    fun loadProfile(usernameToLoad: String) {
        askPosts.clear()
        materialPosts.clear()
        eventsPosts.clear()
        expertises.clear()

        isLoading = true

        viewModelScope.launch {
            try {
                val user = ProfileApi.getUserInformation(usernameToLoad)

                username = user.username
                name = user.name
                badge = user.currentBadge
                xp = user.xp

                for (post in AskApi.getUserAsks(username)) {
                    askPosts.add(post)
                }

                for (post in MaterialApi.getUserMaterials(username)) {
                    materialPosts.add(post)
                }

                for (post in EventApi.getUserEvents(username)) {
                    eventsPosts.add(post)
                }

                println(eventsPosts)

                for (expertise in user.expertises) {
                    expertises.add(expertise)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                feedback = ErrorParser.from(e.message)
            }
        }

        isLoading = false
    }
}