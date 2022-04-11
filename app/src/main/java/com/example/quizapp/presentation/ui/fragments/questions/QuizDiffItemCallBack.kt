package com.example.quizapp.presentation.ui.fragments.questions

import androidx.recyclerview.widget.DiffUtil
import com.example.quizapp.domain.entities.QuizEntity

class QuizDiffItemCallBack : DiffUtil.ItemCallback<QuizEntity>() {
    override fun areItemsTheSame(oldItem: QuizEntity, newItem: QuizEntity): Boolean {
        return oldItem.question == newItem.question
    }

    override fun areContentsTheSame(oldItem: QuizEntity, newItem: QuizEntity): Boolean {
        return oldItem == newItem
    }
}