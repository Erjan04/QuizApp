package com.example.quizapp.di

import android.content.Context
import com.example.quizapp.data.local.room.AppDatabase
import com.example.quizapp.data.local.room.HistoryDao
import com.example.quizapp.data.local.room.RoomClient
import com.example.quizapp.data.repository.HistoryRepositoryImpl
import com.example.quizapp.domain.repositories.history.AddHistoryRepository
import com.example.quizapp.domain.repositories.history.DeleteHistoryRepository
import com.example.quizapp.domain.repositories.history.GetHistoryRepositories
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context) = RoomClient().provideRoom(context)

    @Singleton
    @Provides
    fun provideDao(appDatabase: AppDatabase): HistoryDao {
        return RoomClient().provideHistoryDao(appDatabase)
    }

    @Singleton
    @Provides
    fun provideGetHistoryRepository(historyDao: HistoryDao): GetHistoryRepositories {
        return HistoryRepositoryImpl(historyDao)
    }

    @Singleton
    @Provides
    fun provideAddHistoryRepository(historyDao: HistoryDao): AddHistoryRepository {
        return HistoryRepositoryImpl(historyDao)
    }

    @Singleton
    @Provides
    fun provideDeleteHistoryRepository(historyDao: HistoryDao): DeleteHistoryRepository {
        return HistoryRepositoryImpl(historyDao)
    }

}