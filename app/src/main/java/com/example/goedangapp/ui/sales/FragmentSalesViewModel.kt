package com.example.goedangapp.ui.sales

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.goedangapp.repository.CustomData
import com.example.goedangapp.repository.GoedangRepo
import com.example.goedangapp.response.LastEntry
import com.example.goedangapp.util.ResultState

class FragmentSalesViewModel(private val repository: GoedangRepo) : ViewModel() {
    val itemLastEntries: LiveData<ResultState<List<CustomData>>> =
        repository.getItemLastEntryData()
}