package com.example.quizapp.domain.usecases.history

import com.example.quizapp.domain.entities.HistoryEntity
import com.example.quizapp.domain.repositories.history.AddHistoryRepository
import javax.inject.Inject

class AddHistoryUseCase @Inject constructor(private val repository: AddHistoryRepository) {

    suspend fun addHistory(historyEntity: HistoryEntity) {
        repository.addHistory(historyEntity)
    }

}