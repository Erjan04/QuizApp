package com.example.quizapp.presentation.ui.fragments.questions

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
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
            binding.btnAnswer1.text = listQuiz[0]
            binding.btnAnswer1.text = listQuiz[1]
            binding.btnAnswer1.text = listQuiz[2]
            binding.btnAnswer1.text = listQuiz[3]

            enabledBtn(true)

            binding.btnAnswer1.setOnClickListener {
                checkState(it as Button, quiz)
                enabledBtn(false)
            }
            binding.btnAnswer2.setOnClickListener {
                checkState(it as Button, quiz)
                enabledBtn(false)
            }
            binding.btnAnswer3.setOnClickListener {
                checkState(it as Button, quiz)
                enabledBtn(false)
            }
            binding.btnAnswer4.setOnClickListener {
                checkState(it as Button, quiz)
                enabledBtn(false)
            }
        }

        private fun checkState(button: Button, question: QuizEntity) {
            if (button.text == question.correctAnswer) {
                button.setBackgroundResource(R.drawable.true_answer)
                onItemClick?.invoke(absoluteAdapterPosition, 1)
            } else {
                button.setBackgroundResource(R.drawable.false_answer)
                onItemClick?.invoke(absoluteAdapterPosition, 0)
            }
        }

        private fun enabledBtn(boolean: Boolean) {
            binding.btnAnswer1.isEnabled = boolean
            binding.btnAnswer1.isEnabled = boolean
            binding.btnAnswer1.isEnabled = boolean
            binding.btnAnswer1.isEnabled = boolean
        }
    }
}

