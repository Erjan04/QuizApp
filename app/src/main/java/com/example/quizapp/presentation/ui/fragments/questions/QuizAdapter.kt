package com.example.quizapp.presentation.ui.fragments.questions

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.quizapp.R
import com.example.quizapp.databinding.ItemInMainTaskBinding
import com.example.quizapp.domain.entities.QuizEntity

class QuizAdapter :
    ListAdapter<QuizEntity, QuizAdapter.MultipleViewHolder>(QuizDiffItemCallBack()) {

    var onItemClick: ((pos: Int, answer: Int) -> Unit)? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MultipleViewHolder {
        return MultipleViewHolder(
            ItemInMainTaskBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MultipleViewHolder, position: Int) {
        getItem(position)?.let { holder.onBind(it) }
    }

    inner class MultipleViewHolder(private val binding: ItemInMainTaskBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(quiz: QuizEntity) {
            val listQuiz = mutableListOf<String>()
            listQuiz.add(quiz.correctAnswer)
            quiz.incorrectAnswers.let {
                it.forEach { incorrect ->
                    listQuiz.add(incorrect)
                }
            }
            listQuiz.shuffle()

            binding.tvQuestion.text = quiz.question
            binding.tvAnswer1.text = listQuiz[0]
            binding.tvAnswer2.text = listQuiz[1]
            binding.tvAnswer3.text = listQuiz[2]
            binding.tvAnswer4.text = listQuiz[3]

            enabledBtn(true)

            binding.tvAnswer1.setOnClickListener {
                checkState(it as TextView, quiz)
                enabledBtn(false)
            }
            binding.tvAnswer2.setOnClickListener {
                checkState(it as TextView, quiz)
                enabledBtn(false)
            }
            binding.tvAnswer3.setOnClickListener {
                checkState(it as TextView, quiz)
                enabledBtn(false)
            }
            binding.tvAnswer4.setOnClickListener {
                checkState(it as TextView, quiz)
                enabledBtn(false)
            }
        }

        private fun checkState(text: TextView, question: QuizEntity) {
            if (text.text == question.correctAnswer) {
                text.setBackgroundResource(R.drawable.true_answer)
                onItemClick?.invoke(absoluteAdapterPosition, 1)
            } else {
                text.setBackgroundResource(R.drawable.false_answer)
                onItemClick?.invoke(absoluteAdapterPosition, 0)
            }
        }

        private fun enabledBtn(boolean: Boolean) {
            binding.tvAnswer1.isEnabled = boolean
            binding.tvAnswer2.isEnabled = boolean
            binding.tvAnswer3.isEnabled = boolean
            binding.tvAnswer4.isEnabled = boolean
        }
    }
}

