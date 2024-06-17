package com.example.goedangapp.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.goedangapp.model.UserModel
import com.example.goedangapp.repository.GoedangRepo
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: GoedangRepo) : ViewModel() {

    fun login(email: String, password: String) = repository.login(email, password)

    fun saveUser(user: UserModel) {
        viewModelScope.launch {
            repository.saveUser(user)
        }
    }
}