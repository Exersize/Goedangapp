package com.example.goedangapp.ui.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.goedangapp.repository.GoedangRepo

class SplashScreenViewModel (private val repository: GoedangRepo) : ViewModel() {
    fun getUser() = repository.getUser().asLiveData()
}