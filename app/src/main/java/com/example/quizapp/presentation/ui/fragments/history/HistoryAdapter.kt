package com.example.quizapp.presentation.ui.fragments.history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.quizapp.databinding.ItemInHistoryBinding
import com.example.quizapp.domain.entities.HistoryEntity

class HistoryAdapter :
    ListAdapter<HistoryEntity, HistoryAdapter.HistoryViewHolder>(HistoryDiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        return HistoryViewHolder(
            ItemInHistoryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        getItem(position)?.let { holder.onBind(it) }
    }

    class HistoryViewHolder(private val binding: ItemInHistoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(it: HistoryEntity) {
            binding.tvCategoryName.text = it.category
            binding.correctAnswer.text = it.correctAnswers
            binding.dataWas.text = it.data
            binding.difficultyAnswer.text = it.difficulty
        }

    }
}