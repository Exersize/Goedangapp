package com.example.goedangapp.ui.register

import androidx.lifecycle.ViewModel
import com.example.goedangapp.repository.GoedangRepo

class RegisterViewModel (private val repository: GoedangRepo) : ViewModel() {
    fun register(name: String, email: String, password: String) = repository.register(name, email, password)
}