package com.mindhub.viewmodel.material

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mindhub.model.api.MaterialFakeApi
import com.mindhub.model.api.MaterialRequest
import com.mindhub.model.entities.Expertise
import com.mindhub.services.UserInfo
import com.mindhub.viewmodel.post.PostViewModelInterface
import kotlinx.coroutines.launch

class UpdateMaterialViewModel: ViewModel(), PostViewModelInterface {
    var id: Int = -1
    override var title by mutableStateOf("")
    override var content by mutableStateOf("")
    override var expertise by mutableStateOf(Expertise(""))

    fun update(
        onSuccess: () -> Unit,
        onFailure: (String?) -> Unit
    ) {
        viewModelScope.launch {
            try {
                MaterialFakeApi.update(
                    materialId = id,
                    materialUpdated = MaterialRequest(
                        title = title,
                        content = content,
                        expertise = expertise,
                        user = UserInfo!!
                    )
                )

                onSuccess()
            } catch (e: Exception) {
                onFailure(e.message)
            }
        }
    }

    fun remove(
        onSuccess: () -> Unit,
        onFailure: (String?) -> Unit
    ) {
        viewModelScope.launch {
            try {
                MaterialFakeApi.remove(materialId = id)
                onSuccess()
            } catch (e: Exception) {
                onFailure(e.message)
            }
        }
    }
}