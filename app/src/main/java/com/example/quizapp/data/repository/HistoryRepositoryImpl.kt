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

    override fun addHistory(historyEntity: HistoryEntity) {
        dao.setHistory(historyEntity)
    }

    override fun deleteHistory(historyEntity: HistoryEntity) {
        dao.deleteHistory(historyEntity)
    }

    override fun getHistory(): Flow<List<HistoryEntity>> = dao.getHistory()
}