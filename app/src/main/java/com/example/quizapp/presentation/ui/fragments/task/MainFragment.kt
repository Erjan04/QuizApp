package com.example.quizapp.presentation.ui.fragments.task

import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.quizapp.common.base.BaseFragment
import com.example.quizapp.common.extensions.setDropSpinner
import com.example.quizapp.common.extensions.setOnSeekBar
import com.example.quizapp.databinding.FragmentMainBinding
import com.example.quizapp.domain.entities.CategoryEntity
import com.example.quizapp.presentation.ui.state.UIState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class MainFragment : BaseFragment<FragmentMainBinding>() {

    private val viewModel: MainViewModel by activityViewModels()

    override fun bind(): FragmentMainBinding {
        return FragmentMainBinding.inflate(layoutInflater)
    }

    override fun setupListeners() {

        binding.seekBar.setOnSeekBar {
            binding.amountTv.text = it
        }
        binding.btnStart.setOnClickListener {
            val amount = binding.amountTv.text.toString().toInt()
            val category = binding.categorySpinner.selectedItem as CategoryEntity
            val difficulty = binding.difficultySpinner.selectedItem
            val categoryName = binding.categorySpinner.selectedItem
            findNavController().navigate(
                MainFragmentDirections.actionMainFragmentToQuizFragment(
                    amount, category.id, difficulty = difficulty.toString(), categoryName.toString()
                )
            )
        }
    }

    override fun setupObservers() {
        lifecycleScope.launchWhenCreated {
            viewModel.categoryList.collectLatest {
                when (it) {
                    is UIState.Loading -> {
                    }
                    is UIState.Success -> {
                        val listCategory = arrayListOf<CategoryEntity>()
                        listCategory.add(CategoryEntity("Any Category", -1))
                        it.data.forEach { category ->
                            listCategory.add(category)
                        }
                        binding.categorySpinner.adapter = setDropSpinner(listCategory)
                    }
                    is UIState.Error -> {
                        Toast.makeText(requireContext(), "Error", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    override fun setupUI() {
        val list = arrayListOf<Any>("Any Difficulty", "Easy", "Medium", "Hard")
        binding.difficultySpinner.adapter = setDropSpinner(list)
    }
}