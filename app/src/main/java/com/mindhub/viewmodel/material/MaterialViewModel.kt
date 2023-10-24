package com.mindhub.viewmodel.material

import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mindhub.model.api.MaterialFakeApi
import com.mindhub.model.api.MaterialRequest
import com.mindhub.model.entities.Expertise
import com.mindhub.model.entities.Material
import com.mindhub.model.entities.Post
import com.mindhub.services.UserInfo
import com.mindhub.viewmodel.post.PostViewModelInterface
import kotlinx.coroutines.launch

class MaterialViewModel: ViewModel(), PostViewModelInterface {
    override var title by mutableStateOf("")
    override var content by mutableStateOf("")
    override var expertise by mutableStateOf(Expertise(""))

    override fun create(
        onSuccess: (Post) -> Unit,
        onFailure: (String?) -> Unit,
    ) {
        viewModelScope.launch {
            try {
                val post = MaterialFakeApi.create(
                    MaterialRequest(
                        title = title,
                        content = content,
                        expertise = expertise,
                        user = UserInfo!!
                    )
                )

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
                MaterialFakeApi.update(
                    Material(
                        id = -1,
                        title = title,
                        content = content,
                        expertise = expertise,
                        user = UserInfo!!,
                        score = 0
                    )
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
                MaterialFakeApi.remove(materialId = postId)
                onSuccess()
            } catch (e: Exception) {
                onFailure(e.message)
            }
        }
    }

    override fun getType(): String {
        return "material"
    }
}