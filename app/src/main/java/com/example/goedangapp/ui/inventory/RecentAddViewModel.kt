package com.example.goedangapp.ui.inventory

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.goedangapp.repository.GoedangRepo
import com.example.goedangapp.response.ItemEntryItem
import com.example.goedangapp.util.ResultState

class RecentAddViewModel(private val repo: GoedangRepo) : ViewModel() {
    fun getItemById(id: String) = repo.getItemById(id)

    val itemEntries: LiveData<ResultState<List<ItemEntryItem>>> = repo.filterRecentItemEntry()

}