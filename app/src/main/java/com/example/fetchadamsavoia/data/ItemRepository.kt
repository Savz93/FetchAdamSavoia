package com.example.fetchadamsavoia.data

import com.example.fetchadamsavoia.network.RetrofitInstance

class ItemRepository {
    private val apiService = RetrofitInstance.apiService
    suspend fun getItems(): List<FetchModel> {
        return apiService.getItems()
    }
}