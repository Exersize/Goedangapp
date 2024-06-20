package com.example.goedangapp.ui.inventory

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.goedangapp.repository.GoedangRepo

class StockViewModel (private val repository: GoedangRepo) : ViewModel() {

    private val _tabTitles = MutableLiveData<List<String>>()
    val tabTitles: LiveData<List<String>> get() = _tabTitles

    init {
        initTabTitles()
    }

    private fun initTabTitles() {
        _tabTitles.value = listOf("Current Stock", "Low In Stock", "Recently Add")
    }




}