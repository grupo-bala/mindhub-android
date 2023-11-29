package com.mindhub.viewmodel.material

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.neverEqualPolicy
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mindhub.common.services.ErrorParser
import com.mindhub.model.api.MaterialApi
import com.mindhub.model.entities.Post
import com.mindhub.viewmodel.post.GetPostViewModel
import kotlinx.coroutines.launch

class GetMaterialViewModel : GetPostViewModel() {
    override var post: Post? by mutableStateOf(null, policy = neverEqualPolicy())
    override var feedback by mutableStateOf("")
    override var isLoading by mutableStateOf(false)

    override fun get(id: Int) {
        viewModelScope.launch {
            feedback = ""
            isLoading = true

            try {
                post = MaterialApi.getOne(id)
            } catch (e: Exception) {
                feedback = ErrorParser.from(e.message)
            }

            isLoading = false
        }
    }
}