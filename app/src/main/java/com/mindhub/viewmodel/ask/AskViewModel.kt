package com.mindhub.viewmodel.ask

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mindhub.common.services.CurrentUser
import com.mindhub.model.api.AskFakeApi
import com.mindhub.model.entities.Ask
import com.mindhub.model.entities.Expertise
import com.mindhub.model.entities.Post
import com.mindhub.viewmodel.post.PostViewModelInterface
import kotlinx.coroutines.launch
import java.time.LocalDateTime

class AskViewModel: ViewModel(), PostViewModelInterface {
    override var title by mutableStateOf("")
    override var content by mutableStateOf("")
    override var feedback: String? by mutableStateOf("")
    override var expertise: Expertise? by mutableStateOf(Expertise(""))
    var file by mutableStateOf<Uri?>(null)

    override fun create(
        onSuccess: (Post) -> Unit,
        onFailure: (String?) -> Unit,
    ) {
        viewModelScope.launch {
            try {
                isValid()

                val post = AskFakeApi.create(Ask(
                    id = -1,
                    title = title,
                    content = content,
                    expertise = expertise!!,
                    file = file,
                    score = 0,
                    postDate = LocalDateTime.now(),
                    userScore = 0,
                    user = CurrentUser.user!!
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
        isValid()

        viewModelScope.launch {
            try {
                AskFakeApi.update(Ask(
                    id = postId,
                    title = title,
                    content = content,
                    expertise = expertise!!,
                    user = CurrentUser.user!!,
                    file = file,
                    postDate = LocalDateTime.now(),
                    userScore = 0,
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

    override fun isValid(): Boolean {
        if (!isFilled()) {
            this.feedback = "Preencha todos campos obrigatórios"
            return false
        }
        return true
    }

    override fun isFilled(): Boolean {
        return this.title != "" && this.content != "" && this.expertise != null
    }
}