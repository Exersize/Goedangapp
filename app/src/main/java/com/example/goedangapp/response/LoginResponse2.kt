package com.example.goedangapp.response

import com.google.gson.annotations.SerializedName

data class LoginResponse2(

	@field:SerializedName("user")
	val user: User? = null,

	@field:SerializedName("accessTokens")
	val accessTokens: AccessTokens? = null
) {
	val message: String? = null
}

data class User(

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("fullName")
	val fullName: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("email")
	val email: String? = null,

	@field:SerializedName("updatedAt")
	val updatedAt: String? = null
)

data class AccessTokens(

	@field:SerializedName("abilities")
	val abilities: List<String?>? = null,

	@field:SerializedName("lastUsedAt")
	val lastUsedAt: Any? = null,

	@field:SerializedName("name")
	val name: Any? = null,

	@field:SerializedName("type")
	val type: String? = null,

	@field:SerializedName("expiresAt")
	val expiresAt: Any? = null,

	@field:SerializedName("token")
	val token: String? = null
)

