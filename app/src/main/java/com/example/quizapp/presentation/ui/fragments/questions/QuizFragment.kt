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
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class QuizFragment : BaseFragment<FragmentQuizBinding>() {

    private val args: QuizFragmentArgs by navArgs()
    private val viewModel: QuizViewModel by viewModels()
    private val adapter: QuizAdapter by lazy {
        QuizAdapter()
    }

    override fun setupData() {
        viewModel.fetchQuiz(args.amount, args.category, args.difficulty)
    }

    override fun setupListeners() {

    }

    override fun setupObservers() {
        lifecycleScope.launch {
            viewModel.fetchQuizList.collectLatest {
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
        }
        Log.e("quiz", "setupObservers: ${args.amount}")
    }

    override fun setupUI() {
        binding.rvQuiz.apply {
            layoutManager = object : LinearLayoutManager(context, HORIZONTAL, false) {
                override fun canScrollHorizontally(): Boolean {
                    return false
                }
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