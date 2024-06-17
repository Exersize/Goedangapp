package com.example.goedangapp.response

import com.google.gson.annotations.SerializedName

data class AuthResponse(

	@field:SerializedName("errors")
	val errors: List<ErrorsItem?>? = null
) {
	val message: String = "Login Successful"
}

data class ErrorsItem(

	@field:SerializedName("field")
	val field: String? = null,

	@field:SerializedName("rule")
	val rule: String? = null,

	@field:SerializedName("message")
	val message: String? = null
)
