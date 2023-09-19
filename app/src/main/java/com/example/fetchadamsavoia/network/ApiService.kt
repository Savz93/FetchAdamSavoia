package com.example.fetchadamsavoia.network

import com.example.fetchadamsavoia.data.FetchModel
import retrofit2.http.GET

interface ApiService {
    @GET("hiring.json")
    suspend fun getItems(): List<FetchModel>
}