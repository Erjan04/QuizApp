package com.example.quizapp.presentation.ui.fragments.questions

import android.util.Log
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.quizapp.common.base.BaseFragment
import com.example.quizapp.common.extensions.showToast
import com.example.quizapp.databinding.FragmentQuizBinding
import com.example.quizapp.presentation.ui.state.UIState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class QuizFragment : BaseFragment<FragmentQuizBinding>() {

    private val args: QuizFragmentArgs by navArgs()
    private val viewModel: QuizViewModel by viewModels()
    private var correctAnswer: Int = 0
    private val adapter: QuizAdapter by lazy {
        QuizAdapter()
    }

    override fun setupData() {
        if (args.category == -1 && args.difficulty != "any difficulty") {
            viewModel.fetchQuiz(args.amount, difficulty = args.difficulty)
            Log.e("quized", "category without:")
        } else if (args.difficulty == "any difficulty" && args.category != -1) {
            viewModel.fetchQuiz(args.amount, args.category)
            Log.e("quized", "difficulty without:")
        } else if (args.category == -1 && args.difficulty == "any difficulty") {
            viewModel.fetchQuiz(args.amount)
            Log.e("quized", "setupData: diff and categ no")
        } else {
            viewModel.fetchQuiz(args.amount, args.category, args.difficulty)
            Log.e("quiezed", "setupData: ${args.amount},${args.category},${args.difficulty}")
        }
    }

    override fun setupListeners() {
        adapter.onItemClick = { pos, ans ->

            val positionAdapter = pos + 1
            correctAnswer += ans

            lifecycleScope.launchWhenCreated {
                delay(1000)
                if (adapter.currentList.size - 1 == pos) {
                    val correctResult = "$correctAnswer/${args.amount}"
                    val resultPercent = (correctAnswer.toDouble() / args.amount.toDouble()) * 100
                    val result = "${resultPercent.toInt()}%"

                    findNavController().navigate(
                        QuizFragmentDirections.actionQuizFragmentToResultFragment(
                            args.categoryName,
                            correctResult,
                            result,
                            args.difficulty
                        )
                    )
                } else {
                    binding.rvQuiz.layoutManager?.scrollToPosition(positionAdapter)
                }
            }
        }
    }

    override fun setupObservers() {
        observeQuiz()
    }

    private fun observeQuiz() {
        lifecycleScope.launchWhenCreated {
            viewModel.fetchQuizList.collectLatest {
                binding.progress.isVisible = it is UIState.Loading
                when (it) {
                    is UIState.Loading -> {
                    }
                    is UIState.Error -> {
                        Log.e("Error", "observeQuiz: ${it.message}")
                    }
                    is UIState.Success -> {
                        binding.tvCategory.text = args.categoryName
                        adapter.submitList(it.data)
                        requireContext().showToast("Success")
                        Log.e("Success", "observeQuiz: ${it.data}")
                    }
                }
            }
        }
    }

    override fun setupUI() {
        binding.rvQuiz.apply {
            layoutManager = object : LinearLayoutManager(context, HORIZONTAL, false) {
                override fun canScrollHorizontally() = false
            }
            adapter = this@QuizFragment.adapter
        }
    }

    override fun bind(): FragmentQuizBinding {
        return FragmentQuizBinding.inflate(layoutInflater)
    }
}