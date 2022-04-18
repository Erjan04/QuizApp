package com.example.quizapp.domain.repositories.history

import com.example.quizapp.domain.entities.HistoryEntity

interface AddHistoryRepository {

    suspend fun addHistory(historyEntity: HistoryEntity)

}