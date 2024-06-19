package com.example.goedangapp.ui.input

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.goedangapp.repository.GoedangRepo
import com.example.goedangapp.retrofit.UserPreference

class AddItemViewModel(private val repository: GoedangRepo) : ViewModel() {

    fun addItem(measuringUnit: String, name: String, quantity: Int = 0, userId: String) =
        repository.addItem(measuringUnit, name, quantity, userId)


}