package com.mindhub.viewmodel.ask

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mindhub.model.api.AskFakeApi
import com.mindhub.model.entities.Ask
import com.mindhub.model.entities.Expertise
import com.mindhub.model.entities.Post
import com.mindhub.common.services.UserInfo
import com.mindhub.viewmodel.post.PostViewModelInterface
import kotlinx.coroutines.launch

class AskViewModel: ViewModel(), PostViewModelInterface {
    override var title by mutableStateOf("")
    override var content by mutableStateOf("")
    override var expertise by mutableStateOf(Expertise(""))

    override fun create(
        onSuccess: (Post) -> Unit,
        onFailure: (String?) -> Unit,
    ) {
        viewModelScope.launch {
            try {
                val post = AskFakeApi.create(Ask(
                    id = -1,
                    title = title,
                    content = content,
                    expertise = expertise,
                    score = 0,
                    user = UserInfo!!
                ))

                onSuccess(post)
            } catch (e: Exception) {
                onFailure(e.message)
            }
        }
    }

    override fun update(
        postId: Int,
        onSuccess: () -> Unit,
        onFailure: (String?) -> Unit,
    ) {
        viewModelScope.launch {
            try {
                AskFakeApi.update(Ask(
                    id = postId,
                    title = title,
                    content = content,
                    expertise = expertise,
                    user = UserInfo!!,
                    score = 0
                ))

                onSuccess()
            } catch (e: Exception) {
                onFailure(e.message)
            }
        }
    }

    override fun remove(
        postId: Int,
        onSuccess: () -> Unit,
        onFailure: (String?) -> Unit,
    ) {
        viewModelScope.launch {
            try {
                AskFakeApi.remove(postId)
                onSuccess()
            } catch (e: Exception) {
                onFailure(e.message)
            }
        }
    }

    override fun getType(): String {
        return "pergunta"
    }
}