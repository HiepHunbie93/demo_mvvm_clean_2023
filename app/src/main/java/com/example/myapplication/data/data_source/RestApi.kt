package com.example.myapplication.data.data_source

import com.example.myapplication.data.data_source.dto.DataListDto
import retrofit2.http.GET
import retrofit2.http.Query

interface RestApi {

    @GET("?apikey=b9bd48a6&s=Marvel&type=movie")
    suspend fun getAllDatas(@Query("page")page:String): DataListDto

}