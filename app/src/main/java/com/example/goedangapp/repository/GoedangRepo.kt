package com.example.goedangapp.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.example.goedangapp.model.UserModel
import com.example.goedangapp.response.AuthResponse
import com.example.goedangapp.response.ItemDetailResponse
import com.example.goedangapp.response.ItemResponseItem
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
    fun getItemById(id: String): LiveData<ResultState<ItemDetailResponse>> = liveData {
        emit(ResultState.Loading)
        try{
            val response = apiService.getItemById(id)
            emit(ResultState.Success(response))
        } catch (e: HttpException) {
            Log.d("StoryRepository", "getDetailStory: ${e.message.toString()}")
            emit(ResultState.Error(e.message.toString()))
        }
    }

    fun getItem(): LiveData<ResultState<List<ItemResponseItem>>> = liveData {
        emit(ResultState.Loading)
        try {
            val response = apiService.getItem()
            emit(ResultState.Success(response))
        } catch (e: HttpException) {
            emit(ResultState.Error(e.toString()))
        } catch (e: Exception) {
            emit(ResultState.Error(e.toString()))
        }
    }

    fun filterLowStock() = liveData {
        emit(ResultState.Loading)
        try {
            val response = apiService.getItem()

            val filteredItems = response.filter {
                it.quantity != null && it.threshold != null && it.quantity < it.threshold
            }.sortedBy {
                it.quantity
            }
            emit(ResultState.Success(filteredItems))
        } catch (e: HttpException) {
            emit(ResultState.Error(e.toString()))
        }
    }

    fun filterRecentItemEntry() = liveData {
        emit(ResultState.Loading)
        try {
            val response = apiService.getItemEntries()

            val sortedItem = response.sortedByDescending { it.createdAt }
            emit(ResultState.Success(sortedItem))
        } catch (e: HttpException) {
            emit(ResultState.Error(e.toString()))
        }
    }

    fun addItem(measuringUnit: String, name: String, quantity: Int, userId: String) = liveData {
        emit(ResultState.Loading)
        try {
            val successResponse = apiService.addItem(measuringUnit, name, quantity, userId)
            emit(ResultState.Success(successResponse))
        } catch (e: HttpException) {
            emit(ResultState.Error(e.toString()))
        }
    }

    fun addItemEntry(itemId: String, userId: String, inOut: String, quantity: Int, price: Int, total: Int) = liveData {
        emit(ResultState.Loading)
        try {
            val successResponse = apiService.addItemEntry(itemId, userId, inOut, quantity, price, total)
            emit(ResultState.Success(successResponse))
        } catch (e: HttpException) {
            emit(ResultState.Error(e.toString()))
        }
    }

    suspend fun getUserId(): String {
        val user = userPreference.getUser().first()
        return user.userId
    }


    fun fetchItemName(): LiveData<ResultState<List<String>>> {
        return liveData {
            emit(ResultState.Loading)
            try {
                val response = apiService.getItem()
                val itemMap = response.associateBy(ItemResponseItem::name, ItemResponseItem::id)

                val itemNamesList = itemMap.keys.filterNotNull().toList()

                emit(ResultState.Success(itemNamesList))
            } catch (e: Exception) {
                emit(ResultState.Error(e.toString()))
            }
        }
    }

    suspend fun fetchItemId(name: String?): String {
        return try {
            val response = apiService.getItem()
            val itemMap = response.associateBy(ItemResponseItem::name, ItemResponseItem::id)
            val id = itemMap[name]
            id ?: throw Exception("Name not found")
        } catch (e: Exception) {
            throw Exception("Error fetching item ID: ${e.message}")
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