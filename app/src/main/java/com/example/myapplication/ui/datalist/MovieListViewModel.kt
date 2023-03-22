package com.example.myapplication.ui.datalist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.domain.use_case.MovieListUseCase
import com.example.myapplication.ui.datalist.list.MovieListState
import com.example.myapplication.util.ResponseState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel @Inject constructor(
    private val movieListUseCase: MovieListUseCase
) : ViewModel(){

    private val movieListValue = MutableStateFlow(MovieListState())
    var _movieListValue : StateFlow<MovieListState> = movieListValue

    fun getAllData(page:String)=viewModelScope.launch(Dispatchers.IO){
        movieListUseCase(page).collect {
            when(it){
                is ResponseState.Success ->{
                    movieListValue.value = MovieListState( moviesList = it.data?: emptyList())
                }
                is ResponseState.Loading ->{
                    movieListValue.value = MovieListState(isLoading = true)
                }
                is ResponseState.Error ->{
                    movieListValue.value = MovieListState(error = it.message?:"An Unexpected Error")
                }
            }
        }
    }
}