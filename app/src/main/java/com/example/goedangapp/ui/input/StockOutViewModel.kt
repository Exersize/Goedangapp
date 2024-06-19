package com.example.goedangapp.ui.input

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.goedangapp.repository.GoedangRepo
import com.example.goedangapp.util.ResultState

class StockOutViewModel(private val repository: GoedangRepo) : ViewModel() {
    fun fetchItemName(): LiveData<ResultState<List<String>>> {
        return repository.fetchItemName()
    }

    suspend fun fetchItemId(name: String?): String {
        return repository.fetchItemId(name)
    }

    suspend fun getUserId() = repository.getUserId()

    fun addItemEntry(
        itemId: String,
        userId: String,
        inOut: String,
        quantity: Int,
        price: Int,
        total: Int
    ) =
        repository.addItemEntry(itemId, userId, inOut, quantity, price, total)
}