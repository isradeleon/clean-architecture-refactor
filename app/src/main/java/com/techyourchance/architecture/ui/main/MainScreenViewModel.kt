package com.techyourchance.architecture.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.techyourchance.architecture.domain.use_cases.IsQuestionInFavoritesUseCase
import com.techyourchance.architecture.domain.use_cases.ToggleFavoriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val isQuestionInFavoritesUseCase: IsQuestionInFavoritesUseCase,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase
): ViewModel() {
    fun isQuestionInFavorites(questionId: String): Flow<Boolean> {
        return isQuestionInFavoritesUseCase.isIt(questionId)
    }

    fun toggleFavoriteQuestion(
        questionId: String, questionTitle: String
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            toggleFavoriteUseCase.toggle(
                questionId, questionTitle
            )
        }
    }
}