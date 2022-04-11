package com.example.quizapp.presentation.ui.fragments.task

import com.example.quizapp.common.base.BaseViewModel
import com.example.quizapp.domain.entities.CategoryEntity
import com.example.quizapp.domain.usecases.main.GetCategoryUseCase
import com.example.quizapp.presentation.ui.state.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val getCategoryUseCase: GetCategoryUseCase) :
    BaseViewModel() {

    private val _categoryList = MutableStateFlow<UIState<List<CategoryEntity>>>(UIState.Loading())
    val categoryList: StateFlow<UIState<List<CategoryEntity>>> get() = _categoryList

    init {
        fetchCategory()
    }

    private fun fetchCategory() {
        loadData(_categoryList) {
            getCategoryUseCase()
        }
    }
}