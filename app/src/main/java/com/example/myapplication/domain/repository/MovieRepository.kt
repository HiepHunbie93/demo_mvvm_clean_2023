package com.example.myapplication.domain.repository

import com.example.myapplication.data.data_source.dto.DataListDto

interface MovieRepository {

    suspend fun getAllDatas(page:String): DataListDto


}