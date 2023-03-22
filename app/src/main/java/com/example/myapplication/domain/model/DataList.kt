package com.example.myapplication.domain.model
data class DataList (
	val searchModels : List<SearchModel>,
	val totalResults : Int?,
	val response : Boolean?
)