package com.example.goedangapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.goedangapp.repository.GoedangRepo
import kotlinx.coroutines.launch

class ProfileViewModel (private val repository: GoedangRepo) : ViewModel() {

    fun logout() {
        viewModelScope.launch {
            repository.logout()
        }
    }

    fun getUser() = repository.getUser().asLiveData()
}