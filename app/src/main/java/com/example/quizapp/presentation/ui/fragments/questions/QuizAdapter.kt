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

    val onItemClick: ((QuizEntity) -> Unit)? = null

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
        getItem(position)?.let { holder.onItemsClick(it) }
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

            setBtnColor()
            enabledBtn(true)
        }

        private fun setBtnColor() {
            binding.btnAnswer1.setBackgroundColor(R.drawable.default_answer)
            binding.btnAnswer2.setBackgroundColor(R.drawable.default_answer)
            binding.btnAnswer3.setBackgroundColor(R.drawable.default_answer)
            binding.btnAnswer4.setBackgroundColor(R.drawable.default_answer)
        }

        private fun responseCheck(button: Button, question: QuizEntity) {
            button.setBackgroundResource(R.drawable.default_answer)
            if (button.text == question.correctAnswer) {
                button.setBackgroundResource(R.drawable.true_answer)
                onItemClick?.invoke(getItem(absoluteAdapterPosition))
            } else {
                button.setBackgroundResource(R.drawable.false_answer)
                onItemClick?.invoke(getItem(absoluteAdapterPosition))
            }
        }

        private fun enabledBtn(boolean: Boolean) {
            binding.btnAnswer1.isEnabled = boolean
            binding.btnAnswer1.isEnabled = boolean
            binding.btnAnswer1.isEnabled = boolean
            binding.btnAnswer1.isEnabled = boolean
        }

        fun onItemsClick(question: QuizEntity) {
            binding.btnAnswer1.setOnClickListener { it ->
                responseCheck(it as Button, question)
                enabledBtn(false)
            }
            binding.btnAnswer2.setOnClickListener { it ->

                responseCheck(it as Button, question)
                enabledBtn(false)
            }
            binding.btnAnswer3.setOnClickListener { it ->
                responseCheck(it as Button, question)
                enabledBtn(false)
            }
            binding.btnAnswer4.setOnClickListener { it ->
                responseCheck(it as Button, question)
                enabledBtn(false)
            }
        }
    }
}

