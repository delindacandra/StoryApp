package com.dicoding.storyapp.data.api

import com.google.gson.annotations.SerializedName
data class AddStoryResponse(
	@field:SerializedName("error")
	val error: Boolean,

	@field:SerializedName("message")
	val message: String
)

