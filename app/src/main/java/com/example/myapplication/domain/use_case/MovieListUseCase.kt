package com.example.myapplication.domain.use_case

import com.example.myapplication.domain.model.SearchModel
import com.example.myapplication.domain.repository.MovieRepository
import com.example.myapplication.util.ResponseState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class MovieListUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    operator fun invoke(page:String): Flow<ResponseState<List<SearchModel>>> = flow {
        try {
            emit(ResponseState.Loading<List<SearchModel>>())
            val aa = repository.getAllDatas(page)
            aa.let {
                val movies = it.search.map { t ->
                    t.toSearch()
                }
                emit(ResponseState.Success<List<SearchModel>>(movies))
            }

        } catch (e: HttpException) {
            emit(ResponseState.Error<List<SearchModel>>(e.localizedMessage ?: "An Unexpected Error"))
        } catch (e: IOException) {
            emit(ResponseState.Error<List<SearchModel>>("Internet Error"))
        }
    }
}