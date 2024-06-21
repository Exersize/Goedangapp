package com.example.goedangapp.ui.sales

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.goedangapp.repository.CustomData
import com.example.goedangapp.repository.GoedangRepo
import com.example.goedangapp.response.ItemEntryItem
import com.example.goedangapp.util.ResultState

class SalesDetailViewModel(private val repository: GoedangRepo) : ViewModel() {

    fun getItemById(id: String) = repository.getItemById(id)

    fun getItemEntriesById(id: String) = repository.getItemEntriesById(id)
}