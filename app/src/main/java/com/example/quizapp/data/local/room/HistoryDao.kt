package com.example.quizapp.data.local.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.quizapp.domain.entities.HistoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface HistoryDao {

    @Query("SELECT * FROM historyentity ORDER BY data ASC")
    fun getHistory(): Flow<List<HistoryEntity>>

    @Insert
    fun setHistory(historyEntity: HistoryEntity)

    @Delete
    fun deleteHistory(historyEntity: HistoryEntity)

}