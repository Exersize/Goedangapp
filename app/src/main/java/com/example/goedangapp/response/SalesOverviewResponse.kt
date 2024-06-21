package com.example.goedangapp.response

import com.google.gson.annotations.SerializedName

data class SalesOverviewResponse(

	@field:SerializedName("SalesOverviewResponse")
	val salesOverviewResponse: List<SalesOverviewResponseItem?>? = null
)

data class SalesOverviewResponseItem(

	@field:SerializedName("date")
	val date: String? = null,

	@field:SerializedName("totalSales")
	val totalSales: Int? = null
)
