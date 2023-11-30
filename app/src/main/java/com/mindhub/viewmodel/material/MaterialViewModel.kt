package com.mindhub.viewmodel.material

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mindhub.common.ext.getTime
import com.mindhub.common.services.CurrentUser
import com.mindhub.model.api.MaterialApi
import com.mindhub.model.api.MaterialRequest
import com.mindhub.model.entities.Expertise
import com.mindhub.model.entities.Material
import com.mindhub.model.entities.Post
import com.mindhub.viewmodel.post.PostViewModelInterface
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.ZoneOffset

class MaterialViewModel: ViewModel(), PostViewModelInterface {
    override var title by mutableStateOf("")
    override var content by mutableStateOf("")
    override var feedback: String? by mutableStateOf("")
    override var expertise: Expertise? by mutableStateOf(null)

    override fun create(
        onSuccess: (Post) -> Unit,
        onFailure: (String?) -> Unit,
    ) {
        isValid()
        viewModelScope.launch {
            try {
                val post = MaterialApi.create(
                    MaterialRequest(
                        title = title,
                        content = content,
                        expertise = expertise!!.title,
                        postDate = "${LocalDateTime.now().toEpochSecond(ZoneOffset.UTC)}"
                    )
                )

                println(post.id)

                onSuccess(post)
            } catch (e: Exception) {
                e.printStackTrace()
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
                isValid()

                MaterialApi.update(
                    MaterialRequest(
                        title = title,
                        content = content,
                        expertise = expertise!!.title,
                        postDate = "${LocalDateTime.now().toEpochSecond(ZoneOffset.UTC)}"
                    ),
                    materialId = postId
                )

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
                MaterialApi.remove(materialId = postId)
                onSuccess()
            } catch (e: Exception) {
                onFailure(e.message)
            }
        }
    }

    override fun getType(): String {
        return "material"
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