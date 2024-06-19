package com.example.goedangapp.ui.input

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.goedangapp.repository.GoedangRepo
import com.example.goedangapp.util.ResultState

class StockInViewModel(private val repository: GoedangRepo) : ViewModel() {
    fun fetchItemName(): LiveData<ResultState<List<String>>> {
        return repository.fetchItemName()
    }
}