package com.example.goedangapp.ui.input

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.goedangapp.repository.GoedangRepo
import com.example.goedangapp.retrofit.ApiService

class StockInputViewModel(private val repository: GoedangRepo) : ViewModel() {

    private val _tabTitles = MutableLiveData<List<String>>()
    val tabTitles: LiveData<List<String>> get() = _tabTitles

    init {
        initTabTitles()
    }

    private fun initTabTitles() {
        _tabTitles.value = listOf("Stock In", "Stock Out")
    }




}