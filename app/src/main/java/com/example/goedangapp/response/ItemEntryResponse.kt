package com.example.goedangapp.response

import com.google.gson.annotations.SerializedName

data class ItemEntryResponse(

	@field:SerializedName("data")
	val data: ItemEntryItem? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class ItemEntryItem(

	@field:SerializedName("itemId")
	val itemId: String? = null,

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("total")
	val total: String? = null,

	@field:SerializedName("inOut")
	val inOut: String? = null,

	@field:SerializedName("quantity")
	val quantity: String? = null,

	@field:SerializedName("price")
	val price: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("userId")
	val userId: String? = null,

	@field:SerializedName("updatedAt")
	val updatedAt: String? = null
)
