package com.example.quizapp.presentation.ui.fragments.history

import androidx.recyclerview.widget.DiffUtil
import com.example.quizapp.domain.entities.HistoryEntity

class HistoryDiffCallBack : DiffUtil.ItemCallback<HistoryEntity>() {
    override fun areItemsTheSame(oldItem: HistoryEntity, newItem: HistoryEntity): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: HistoryEntity, newItem: HistoryEntity): Boolean {
        return oldItem == newItem
    }
}