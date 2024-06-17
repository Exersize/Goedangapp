package com.example.goedangapp.repository

import androidx.lifecycle.liveData
import com.example.goedangapp.model.UserModel
import com.example.goedangapp.response.AuthResponse
import com.example.goedangapp.response.LoginResponse
import com.example.goedangapp.retrofit.ApiConfig
import com.example.goedangapp.retrofit.ApiService
import com.example.goedangapp.retrofit.UserPreference
import com.example.goedangapp.util.ResultState
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import retrofit2.HttpException

class GoedangRepo  private constructor(
    private var apiService: ApiService,
    private val userPreference: UserPreference
){

    fun register(name: String, email: String, password: String) = liveData {
        emit(ResultState.Loading)
        try {
            val successResponse = apiService.register(name, email, password)
            emit(ResultState.Success(successResponse))
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorResponse = Gson().fromJson(errorBody, AuthResponse::class.java)
            emit(ResultState.Error(errorResponse.errors.toString()))
        }
    }

    fun login(email: String, password: String) = liveData<ResultState<Any>>() {
        emit(ResultState.Loading)
        try {
            val successResponse = apiService.login(email, password)
            val newApiService = successResponse.token?.let { ApiConfig.getApiService(it) }
            if (newApiService != null) {
                apiService = newApiService
            }
            emit(ResultState.Success(successResponse))
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorResponse = Gson().fromJson(errorBody, LoginResponse::class.java)
            emit(ResultState.Error(errorResponse?.message?: "An unexpected error occurred"))
        } catch (e: Exception) {
            emit(ResultState.Error("An unexpected error occurred"))
        }
    }

    suspend fun saveUser(user: UserModel) {
        userPreference.saveUser(user)
    }

    fun getUser(): Flow<UserModel> {
        return userPreference.getUser()
    }

    suspend fun logout() {
        userPreference.logout()
    }

    companion object {
        @Volatile
        private var instance: GoedangRepo? = null
        fun getInstance(apiService: ApiService, userPreference: UserPreference) =
            instance ?: synchronized(this) {
                instance ?: GoedangRepo(apiService, userPreference)
            }.also { instance = it }
    }

}