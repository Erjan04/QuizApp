package com.example.quizapp.presentation.ui.fragments.questions

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.quizapp.R
import com.example.quizapp.databinding.ItemBooleanBinding
import com.example.quizapp.databinding.ItemInMainTaskBinding
import com.example.quizapp.domain.entities.QuizEntity

class QuizAdapter :
    ListAdapter<QuizEntity, RecyclerView.ViewHolder>(QuizDiffItemCallBack()) {

    var onItemClick: ((pos: Int, answer: Int) -> Unit)? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        return if (viewType == R.layout.item_in_main_task) {
            MultipleViewHolder(
                ItemInMainTaskBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        } else {
            BooleanViewHolder(
                ItemBooleanBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val quiz = getItem(position)
        if (holder.itemViewType == R.layout.item_in_main_task) {
            holder as MultipleViewHolder
            holder.onBind(quiz)
        } else {
            holder as BooleanViewHolder
            holder.onBind(quiz)
        }
    }

    override fun getItemViewType(position: Int): Int {
        val quiz = getItem(position)
        return if (quiz.type == "multiple") {
            R.layout.item_in_main_task
        } else {
            R.layout.item_boolean
        }
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

            val count = "${absoluteAdapterPosition + 1}/${currentList.size}"
            binding.tvProgress.text = count
            binding.progressLinear.max = currentList.size
            binding.progressLinear.progress = absoluteAdapterPosition + 1

            binding.tvQuestion.text = quiz.question
            binding.tvAnswer1.text = listQuiz[0]
            binding.tvAnswer2.text = listQuiz[1]
            binding.tvAnswer3.text = listQuiz[2]
            binding.tvAnswer4.text = listQuiz[3]

            binding.btnSkip.setOnClickListener {
                onItemClick?.invoke(absoluteAdapterPosition, 0)
            }
            enabledBtn(true)
            defColor()

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

        private fun defColor() {
            binding.tvAnswer1.setBackgroundResource(R.drawable.default_answer)
            binding.tvAnswer2.setBackgroundResource(R.drawable.default_answer)
            binding.tvAnswer3.setBackgroundResource(R.drawable.default_answer)
            binding.tvAnswer4.setBackgroundResource(R.drawable.default_answer)
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

    inner class BooleanViewHolder(
        private val binding: ItemBooleanBinding
    ) :
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

            enabledBtn(true)
            defColor()

            val count = "${absoluteAdapterPosition + 1}/${currentList.size}"
            binding.tvProgress.text = count
            binding.progressLinear.max = currentList.size
            binding.progressLinear.progress = absoluteAdapterPosition + 1

            binding.tvQuestion.text = quiz.question
            binding.tvTrue.text = listQuiz[0]
            binding.tvFalse.text = listQuiz[1]

            binding.tvTrue.setOnClickListener {
                checkState(it as TextView, quiz)
                enabledBtn(false)
            }
            binding.tvFalse.setOnClickListener {
                checkState(it as TextView, quiz)
                enabledBtn(false)
            }
            binding.btnSkip.setOnClickListener {
                onItemClick?.invoke(absoluteAdapterPosition, 0)
            }
        }

        private fun defColor() {
            binding.tvFalse.setBackgroundResource(R.drawable.default_answer)
            binding.tvTrue.setBackgroundResource(R.drawable.default_answer)
        }

        private fun enabledBtn(boolean: Boolean) {
            binding.tvTrue.isEnabled = boolean
            binding.tvFalse.isEnabled = boolean
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

    }
}


