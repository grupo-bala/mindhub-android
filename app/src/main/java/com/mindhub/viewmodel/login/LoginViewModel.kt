package com.mindhub.viewmodel.login

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.datastore.core.DataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mindhub.common.services.CurrentUser
import com.mindhub.common.services.StoreData
import com.mindhub.model.api.LoginRequest
import com.mindhub.model.api.ProfileApi
import com.mindhub.model.api.UserApi
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {
    var email by mutableStateOf("")
    var password by mutableStateOf("")
    var rememberMe by mutableStateOf(false)
    var feedback by mutableStateOf("")
    var isLoading by mutableStateOf(false)

    fun login(
        context: Context,
        onSuccess: () -> Unit,
        onFailure: (String?) -> Unit
    ) {
        viewModelScope.launch {
            try {
                isLoading = true
                feedback = ""
                UserApi.login(LoginRequest(email, password))

                if (rememberMe) {
                    val storeDate = StoreData(context)
                    storeDate.saveData(CurrentUser.user!!.username, CurrentUser.token)
                }

                onSuccess()
            } catch (e: Exception) {
                onFailure(e.message)
            }

            password = ""
            isLoading = false
        }
    }

    fun setSavedUser(context: Context, onSuccess: () -> Unit) {
        viewModelScope.launch {
            val storeData = StoreData(context)
            storeData.getUsername.collect { username ->
                storeData.getToken.collect { token ->
                    val user = ProfileApi.getUserInformation(username!!)
                    CurrentUser.user = user
                    CurrentUser.token = token!!

                    onSuccess()
                }
            }
        }
    }
}