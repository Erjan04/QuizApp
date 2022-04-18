package com.example.quizapp.domain.repositories.history

import com.example.quizapp.domain.entities.HistoryEntity
import kotlinx.coroutines.flow.Flow

interface GetHistoryRepositories {

    suspend fun getHistory(): Flow<List<HistoryEntity>>

}