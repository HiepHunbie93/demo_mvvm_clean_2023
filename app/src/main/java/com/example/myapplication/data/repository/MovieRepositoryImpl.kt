package com.example.myapplication.data.repository

import com.example.myapplication.data.data_source.dto.DataListDto
import com.example.myapplication.data.data_source.RestApi
import com.example.myapplication.domain.repository.MovieRepository
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val api : RestApi
) : MovieRepository{
    override suspend fun getAllDatas(page:String): DataListDto {
        return api.getAllDatas(page=page)
    }
}