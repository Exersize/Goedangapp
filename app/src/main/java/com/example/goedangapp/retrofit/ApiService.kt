package com.example.goedangapp.retrofit

import com.example.goedangapp.response.AuthResponse
import com.example.goedangapp.response.LoginResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiService {


    @FormUrlEncoded
    @POST("register")
    suspend fun register(
        @Field("full_name") full_name: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): AuthResponse

    @FormUrlEncoded
    @POST("login")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): LoginResponse
}