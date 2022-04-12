package com.example.quizapp.domain.repositories.main

import com.example.quizapp.common.resource.Resource
import com.example.quizapp.domain.entities.CategoryEntity
import kotlinx.coroutines.flow.Flow

interface MainRepository {

    fun getCategory(): Flow<Resource<List<CategoryEntity>>>

}