package com.example.quizapp.presentation.ui.fragments.questions

import android.util.Log
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.quizapp.common.base.BaseFragment
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
        viewModel.fetchQuiz(args.amount, args.category, args.difficulty)
        Log.e("TAG", "setupData: ${args.amount},${args.category},${args.difficulty}")
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
                    val result = "${resultPercent.toInt()}"

                    findNavController().navigate(
                        QuizFragmentDirections.actionQuizFragmentToResultFragment(
                            args.categoryName,
                            correctResult,
                            result
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
                        binding.ibBack.isVisible = false
                    }
                    is UIState.Error -> {
                        Log.e("Error", "observeQuiz: ${it.message}")
                        binding.ibBack.isVisible = false
                    }
                    is UIState.Success -> {
                        binding.ibBack.isVisible = true
                        binding.tvCategory.text = args.categoryName
                        adapter.submitList(it.data)
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
        binding.ibBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    override fun bind(): FragmentQuizBinding {
        return FragmentQuizBinding.inflate(layoutInflater)
    }
}