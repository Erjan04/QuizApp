package com.example.quizapp.data.network.apiservices

import com.example.quizapp.domain.entities.CategoryResponse
import retrofit2.Response
import retrofit2.http.GET

interface MainService {

    @GET("api_category.php")
    suspend fun getCategory(): Response<CategoryResponse>

}