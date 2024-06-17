package com.example.goedangapp.repository

import android.content.Context
import com.example.goedangapp.retrofit.ApiConfig
import com.example.goedangapp.retrofit.UserPreference
import com.example.goedangapp.retrofit.dataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

object Injection {
    fun provideRepository(context: Context): GoedangRepo {
        val pref = UserPreference.getInstance(context.dataStore)
        val user = runBlocking { pref.getUser().first() }
        val apiService = ApiConfig.getApiService(user.token)
        return GoedangRepo.getInstance(apiService, pref)
    }
}