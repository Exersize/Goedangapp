package com.example.goedangapp.response

import com.google.gson.annotations.SerializedName

data class ItemResponse(

	@field:SerializedName("ItemResponse")
	val itemResponse: List<ItemResponseItem?>? = null
)

data class ItemResponseItem(

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("quantity")
	val quantity: Int? = null,

	@field:SerializedName("measuringUnit")
	val measuringUnit: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("userId")
	val userId: String? = null,

	@field:SerializedName("updatedAt")
	val updatedAt: String? = null
)
