package com.example.myapplication.data.data_source.dto

import com.example.myapplication.domain.model.SearchModel
import com.google.gson.annotations.SerializedName

data class SearchDto (
	@SerializedName("Title")
	val title : String?,
	@SerializedName("Year")
	val year : Int,
	@SerializedName("imdbID")
	val imdbID : String?,
	@SerializedName("Type")
	val type : String?,
	@SerializedName("Poster")
	val poster : String?
){
	fun toSearch(): SearchModel {
		return SearchModel(
			title = title?: "",
			year = year?: 0,
			imdbID = imdbID?: "",
			type = type?: "",
			poster = poster ?: ""
		)
	}
}