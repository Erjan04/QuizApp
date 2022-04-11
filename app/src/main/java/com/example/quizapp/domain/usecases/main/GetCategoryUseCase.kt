package com.example.quizapp.domain.usecases.main

import com.example.quizapp.domain.repositories.main.MainRepository
import javax.inject.Inject

class GetCategoryUseCase @Inject constructor(private val repository: MainRepository) {

    operator fun invoke() = repository.getCategory()

}