package com.example.quizapp.data.repository

import com.example.quizapp.common.resource.Resource
import com.example.quizapp.data.network.apiservices.QuizService
import com.example.quizapp.domain.entities.QuizEntity
import com.example.quizapp.domain.repositories.quiz.QuizRepository
import it.czerwinski.android.hilt.annotations.BoundTo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@BoundTo(QuizRepository::class)
class QuizRepositoryImpl @Inject constructor(private val service: QuizService) :
    QuizRepository {

    override fun getQuiz(
        amount: Int?,
        category: Int?,
        difficulty: String?
    ): Flow<Resource<List<QuizEntity>>> = flow {
        emit(Resource.Loading())
        val response = service.getQuiz(amount, category, difficulty)
        if (response.isSuccessful) {
            val body = response.body()
            body?.results?.let { emit(Resource.Success(data = it)) }
        } else {
            emit(Resource.Error(data = null, message = "Error(("))
        }
    }
}