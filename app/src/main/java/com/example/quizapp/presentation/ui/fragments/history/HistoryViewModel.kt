package com.example.quizapp.presentation.ui.fragments.history

import androidx.lifecycle.viewModelScope
import com.example.quizapp.common.base.BaseViewModel
import com.example.quizapp.domain.entities.HistoryEntity
import com.example.quizapp.domain.usecases.history.AddHistoryUseCase
import com.example.quizapp.domain.usecases.history.DeleteHistoryUseCase
import com.example.quizapp.domain.usecases.history.GetHistoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val getHistoryUseCase: GetHistoryUseCase,
    private val addHistoryUseCase: AddHistoryUseCase,
    private val deleteHistoryUseCase: DeleteHistoryUseCase
) : BaseViewModel() {

    private val _historyList = MutableStateFlow<List<HistoryEntity>>(mutableListOf())
    val historyList: StateFlow<List<HistoryEntity>> get() = _historyList

    init {
        fetchHistory()
    }

    fun fetchHistory() {
        viewModelScope.launch {
            getHistoryUseCase.invoke().collect {
                _historyList.value = it
            }
        }
    }

    fun addHistory(historyEntity: HistoryEntity) {
        viewModelScope.launch {
            addHistoryUseCase.addHistory(historyEntity)
        }
    }

    fun deleteHistory(historyEntity: HistoryEntity) {
        viewModelScope.launch {
            deleteHistoryUseCase.deleteHistory(historyEntity)
        }
    }

}