package com.example.goedangapp.response

import com.google.gson.annotations.SerializedName


data class LoginResponse(

	@field:SerializedName("abilities")
	val abilities: List<String?>? = null,

	@field:SerializedName("lastUsedAt")
	val lastUsedAt: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("type")
	val type: String? = null,

	@field:SerializedName("expiresAt")
	val expiresAt: String? = null,

	@field:SerializedName("token")
	val token: String? = null
) {
	val message: String? = null
}



