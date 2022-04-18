package com.example.quizapp.presentation.ui.fragments.result

import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.quizapp.common.base.BaseFragment
import com.example.quizapp.databinding.FragmentResultBinding

class ResultFragment : BaseFragment<FragmentResultBinding>() {

    private val args: ResultFragmentArgs by navArgs()

    override fun setupListeners() {
        binding.btnFinish.setOnClickListener {
            findNavController().navigate(
                ResultFragmentDirections.actionResultFragmentToHistoryFragment(
                    args.category, args.difficultyName, args.corAnswer
                )
            )
        }
    }

    override fun setupObservers() {

    }

    override fun setupUI() {
        binding.difficultyTvInResult.text = args.difficultyName
        binding.correctAnswerTvInResult.text = args.corAnswer
        binding.tvCategoryName.text = args.category
        binding.resultTvInPercent.text = args.result
    }

    override fun bind(): FragmentResultBinding {
        return FragmentResultBinding.inflate(layoutInflater)
    }

}