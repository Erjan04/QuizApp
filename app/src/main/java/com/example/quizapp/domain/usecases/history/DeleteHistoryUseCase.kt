package com.example.quizapp.domain.usecases.history

import com.example.quizapp.domain.entities.HistoryEntity
import com.example.quizapp.domain.repositories.history.DeleteHistoryRepository
import javax.inject.Inject

class DeleteHistoryUseCase @Inject constructor(private val repository: DeleteHistoryRepository) {

    fun deleteHistory(historyEntity: HistoryEntity){
        repository.deleteHistory(historyEntity)
    }

}