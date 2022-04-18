package com.example.quizapp.domain.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class HistoryEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val difficulty: String,
    val category: String,
    val correctAnswers: String,
    val data: String
)