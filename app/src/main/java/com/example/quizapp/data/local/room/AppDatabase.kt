package com.example.quizapp.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.quizapp.domain.entities.HistoryEntity

@Database(entities = [HistoryEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun historyDao(): HistoryDao
}