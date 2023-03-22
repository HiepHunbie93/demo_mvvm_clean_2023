package com.example.myapplication.ui.datalist.list

import com.example.myapplication.domain.model.SearchModel

data class MovieListState(
    val isLoading : Boolean = false,
    val moviesList : List<SearchModel> = emptyList(),
    val error : String = ""
)