package com.mindhub.viewmodel.material

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mindhub.model.entities.Expertise
import com.mindhub.viewmodel.post.PostViewModelInterface
import kotlinx.coroutines.launch

class CreateMaterialViewModel: ViewModel(), PostViewModelInterface {
    var materialTitle by mutableStateOf("")
        override fun setTitle(title: String) {
            this.materialTitle = title
        }

    var materialContent by mutableStateOf("")
        override fun setContent(content: String) {
            this.materialContent = content
        }

    var materialExpertise by mutableStateOf(Expertise(""))
        override fun getExpertise(): String {
            return this.materialExpertise.title
        }
        override fun setExpertise(expertise: Expertise) {
            this.materialExpertise = expertise
        }

    fun create(
        onSuccess: () -> Unit,
        onFailure: (String?) -> Unit,
    ) {
        viewModelScope.launch {
            try {
                onSuccess()
            } catch (e: Exception) {
                onFailure(e.message)
            }
        }
    }
}