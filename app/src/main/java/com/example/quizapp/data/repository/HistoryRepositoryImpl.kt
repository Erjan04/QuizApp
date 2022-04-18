package com.example.quizapp.data.repository

import com.example.quizapp.data.local.room.HistoryDao
import com.example.quizapp.domain.entities.HistoryEntity
import com.example.quizapp.domain.repositories.history.AddHistoryRepository
import com.example.quizapp.domain.repositories.history.DeleteHistoryRepository
import com.example.quizapp.domain.repositories.history.GetHistoryRepositories
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class HistoryRepositoryImpl @Inject constructor(private val dao: HistoryDao) :
    GetHistoryRepositories,
    AddHistoryRepository, DeleteHistoryRepository {

    override suspend fun addHistory(historyEntity: HistoryEntity) {
        dao.setHistory(historyEntity)
    }

    override suspend fun deleteHistory(historyEntity: HistoryEntity) {
        dao.deleteHistory(historyEntity)
    }

    override suspend fun getHistory(): Flow<List<HistoryEntity>> = dao.getHistory()
}