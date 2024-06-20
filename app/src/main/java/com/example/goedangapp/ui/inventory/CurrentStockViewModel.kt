package com.example.goedangapp.ui.inventory

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.goedangapp.repository.GoedangRepo
import com.example.goedangapp.response.ItemResponseItem
import com.example.goedangapp.util.ResultState

class CurrentStockViewModel(private val repository: GoedangRepo) : ViewModel() {

    val items: LiveData<ResultState<List<ItemResponseItem>>> = repository.getItem()
}