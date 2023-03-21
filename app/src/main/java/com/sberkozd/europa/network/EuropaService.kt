package com.sberkozd.europa.network

import com.sberkozd.europa.data.models.Country
import com.sberkozd.europa.data.responses.CountryListResponse
import retrofit2.Response
import retrofit2.http.GET

interface EuropaService {

    @GET("europe")
    suspend fun getList(): Response<List<Country>>
}