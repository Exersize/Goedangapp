package com.example.goedangapp.util

import com.example.goedangapp.repository.CustomData

interface OnItemClickListener {
    fun onItemClick(customData: CustomData)
}