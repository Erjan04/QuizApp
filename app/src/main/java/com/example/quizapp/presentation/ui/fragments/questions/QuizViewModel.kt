package com.example.quizapp.presentation.ui.fragments.questions

import com.example.quizapp.common.base.BaseViewModel
import com.example.quizapp.domain.entities.QuizEntity
import com.example.quizapp.domain.usecases.quiz.GetQuizUseCase
import com.example.quizapp.presentation.ui.state.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class QuizViewModel @Inject constructor(private val getQuizUseCase: GetQuizUseCase) :
    BaseViewModel() {

    private val _fetchQuizList = MutableStateFlow<UIState<List<QuizEntity>>>(UIState.Loading())
    val fetchQuizList: StateFlow<UIState<List<QuizEntity>>> get() = _fetchQuizList

    fun fetchQuiz(
        amount: Int? = null,
        category: Int? = null,
        difficulty: String? = null,
    ) {
        loadData(_fetchQuizList) {
            getQuizUseCase.invoke(amount, category, difficulty)
        }
    }

}