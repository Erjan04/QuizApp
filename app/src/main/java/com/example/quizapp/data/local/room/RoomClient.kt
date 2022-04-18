package com.example.quizapp.data.local.room

import android.content.Context
import androidx.room.Room

class RoomClient {

    fun provideRoom(context: Context) =
        Room.databaseBuilder(context, AppDatabase::class.java, "history_db")
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()

    fun provideHistoryDao(appDatabase: AppDatabase): HistoryDao = appDatabase.historyDao()

}