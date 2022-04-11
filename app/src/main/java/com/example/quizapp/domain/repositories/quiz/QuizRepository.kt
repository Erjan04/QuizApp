package com.example.quizapp.domain.repositories.quiz

import com.example.quizapp.common.resource.Resource
import com.example.quizapp.domain.entities.QuizEntity
import kotlinx.coroutines.flow.Flow

interface QuizRepository {

    fun getQuiz(
        amount: Int?,
        category: Int?,
        difficulty: String?
    ): Flow<Resource<List<QuizEntity>>>

}