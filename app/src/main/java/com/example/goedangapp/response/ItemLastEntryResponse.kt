package com.example.goedangapp.response

import com.google.gson.annotations.SerializedName

data class ItemLastEntryResponse(

	@field:SerializedName("ItemLastEntryResponse")
	val itemLastEntryResponse: List<ItemLastEntryResponseItem?>? = null
)

data class LastEntry(

	@field:SerializedName("itemId")
	val itemId: String? = null,

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("total")
	val total: Int? = null,

	@field:SerializedName("inOut")
	val inOut: String? = null,

	@field:SerializedName("quantity")
	val quantity: Int? = null,

	@field:SerializedName("price")
	val price: Int? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("userId")
	val userId: String? = null,

	@field:SerializedName("updatedAt")
	val updatedAt: String? = null
)

data class ItemEntriesItem(

	@field:SerializedName("itemId")
	val itemId: String? = null,

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("total")
	val total: Int? = null,

	@field:SerializedName("inOut")
	val inOut: String? = null,

	@field:SerializedName("quantity")
	val quantity: Int? = null,

	@field:SerializedName("price")
	val price: Int? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("userId")
	val userId: String? = null,

	@field:SerializedName("updatedAt")
	val updatedAt: String? = null
)

data class ItemLastEntryResponseItem(

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("itemEntries")
	val itemEntries: List<ItemEntriesItem?>? = null,

	@field:SerializedName("quantity")
	val quantity: Int? = null,

	@field:SerializedName("lastEntry")
	val lastEntry: LastEntry? = null,

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
