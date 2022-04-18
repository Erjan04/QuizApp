package com.example.quizapp.domain.repositories.history

import com.example.quizapp.domain.entities.HistoryEntity

interface DeleteHistoryRepository {

    suspend fun deleteHistory(historyEntity: HistoryEntity)

}