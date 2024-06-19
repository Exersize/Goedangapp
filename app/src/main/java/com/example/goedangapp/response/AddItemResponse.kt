package com.example.goedangapp.response

import com.google.gson.annotations.SerializedName

data class AddItemResponse(

	@field:SerializedName("data")
	val data: Data? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class Data(

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("quantity")
	val quantity: String? = null,

	@field:SerializedName("measuringUnit")
	val measuringUnit: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("threshold")
	val threshold: Int? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("userId")
	val userId: String? = null,

	@field:SerializedName("updatedAt")
	val updatedAt: String? = null
)
