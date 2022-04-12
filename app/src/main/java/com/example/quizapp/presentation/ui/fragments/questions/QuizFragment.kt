package com.example.quizapp.presentation.ui.fragments.questions

import android.util.Log
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.quizapp.common.base.BaseFragment
import com.example.quizapp.common.extensions.showToast
import com.example.quizapp.databinding.FragmentQuizBinding
import com.example.quizapp.domain.entities.QuizEntity
import com.example.quizapp.presentation.ui.state.UIState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class QuizFragment : BaseFragment<FragmentQuizBinding>() {

    private val args: QuizFragmentArgs by navArgs()
    private val viewModel: QuizViewModel by viewModels()
    private var correctAnswer: Int = 0
    private val adapter: QuizAdapter by lazy {
        QuizAdapter()
    }

    override fun setupData() {
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
        viewModel.fetchQuizList.flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
            .onEach { handleQuiz(it) }.launchIn(lifecycleScope)
        viewModel.fetchQuiz(args.amount, args.category, args.difficulty)
    }

    private fun handleQuiz(it: UIState<List<QuizEntity>>) {
        binding.progress.isVisible = it is UIState.Loading
        when (it) {
            is UIState.Loading -> {
                Log.e("Loading", "setupObservers: loading")
                binding.ibBack.isVisible = false
            }
            is UIState.Error -> {
                requireContext().showToast("Error")
                binding.ibBack.isVisible = false
            }
            is UIState.Success -> {
                adapter.submitList(it.data)
                binding.tvCategory.text = args.categoryName
                requireContext().showToast("Success")
                binding.ibBack.isVisible = true
                Log.e("success", "setupObservers: ${it.data}")
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