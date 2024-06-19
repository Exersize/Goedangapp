package com.example.goedangapp.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.example.goedangapp.model.UserModel
import com.example.goedangapp.response.AuthResponse
import com.example.goedangapp.response.ItemResponseItem
import com.example.goedangapp.response.LoginResponse
import com.example.goedangapp.response.LoginResponse2
import com.example.goedangapp.retrofit.ApiConfig
import com.example.goedangapp.retrofit.ApiService
import com.example.goedangapp.retrofit.UserPreference
import com.example.goedangapp.util.ResultState
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import retrofit2.HttpException

class GoedangRepo private constructor(
    private var apiService: ApiService,
    private val userPreference: UserPreference
) {

    fun addItem(measuringUnit: String, name: String, quantity: Int, userId: String) = liveData {
        emit(ResultState.Loading)
        try {
            val successResponse = apiService.addItem(measuringUnit, name, quantity, userId)
            emit(ResultState.Success(successResponse))
        } catch (e: HttpException) {
            null
        }
    }

    fun fetchItemName(): LiveData<ResultState<List<String>>> {
        return liveData {
            emit(ResultState.Loading)
            try {
                val response = apiService.getItem()
                val itemMap = response.associateBy(ItemResponseItem::name, ItemResponseItem::id)

                // Extracting names from the itemMap
                val itemNamesList = itemMap.keys.filterNotNull().toList()

                emit(ResultState.Success(itemNamesList))
            } catch (e: Exception) {
                emit(ResultState.Error(e.toString()))
            }
        }
    }

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
            val newApiService = successResponse.accessTokens?.token?.let { ApiConfig.getApiService(it) }
            if (newApiService != null) {
                apiService = newApiService
            }
            emit(ResultState.Success(successResponse))
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorResponse = Gson().fromJson(errorBody, LoginResponse2::class.java)
            emit(ResultState.Error(errorResponse?.message ?: "An unexpected error occurred"))
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