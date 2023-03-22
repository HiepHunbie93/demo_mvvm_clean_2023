package com.example.myapplication.data.data_source.dto

import com.google.gson.annotations.SerializedName

data class DataListDto (
	@SerializedName("Search")
	val search : List<SearchDto>,
	@SerializedName("totalResults")
	val totalResults : Int?,
	@SerializedName("Response")
	val response : Boolean?
)
