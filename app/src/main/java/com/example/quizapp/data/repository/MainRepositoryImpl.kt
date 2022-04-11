package com.example.quizapp.data.repository

import com.example.quizapp.common.resource.Resource
import com.example.quizapp.data.network.apiservices.MainService
import com.example.quizapp.domain.repositories.main.MainRepository
import it.czerwinski.android.hilt.annotations.BoundTo
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@BoundTo(MainRepository::class)
class MainRepositoryImpl @Inject constructor(private val service: MainService) : MainRepository {

    override fun getCategory() = flow {
        emit(Resource.Loading())
        val response = service.getCategory()
        if (response.isSuccessful) {
            val body = response.body()
            body?.category?.let { emit(Resource.Success(data = it)) }
        } else {
            emit(Resource.Error(data = null, message = "Error"))
        }
    }

}