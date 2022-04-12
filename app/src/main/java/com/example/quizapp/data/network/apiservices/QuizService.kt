package com.example.quizapp.data.network.apiservices

import com.example.quizapp.domain.entities.QuizResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface QuizService {

    @GET("api.php")
    suspend fun getQuiz(
        @Query("amount") amount: Int?,
        @Query("category") category: Int?,
        @Query("difficulty") difficulty: String?,
        @Query("type") type: String = "multiple"
    ): Response<QuizResponse>

}