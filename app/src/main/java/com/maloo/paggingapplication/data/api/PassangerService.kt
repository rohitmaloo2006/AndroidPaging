package com.maloo.paggingapplication.data.api

import com.maloo.paggingapplication.data.model.PassengersResponse
import retrofit2.http.Query
import retrofit2.http.GET

interface PassangerService {

    @GET("passenger")
    suspend fun getPassengersData(
        @Query("page") page: Int,
        @Query("size") size: Int = 10
    ): PassengersResponse
}