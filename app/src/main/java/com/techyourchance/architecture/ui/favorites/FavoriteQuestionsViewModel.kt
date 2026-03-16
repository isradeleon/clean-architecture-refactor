package com.techyourchance.architecture.ui.favorites

import androidx.lifecycle.ViewModel
import com.techyourchance.architecture.common.database.daos.FavoriteQuestionDao
import com.techyourchance.architecture.domain.use_cases.ObserveFavoritesUseCase

class FavoriteQuestionsViewModel(
    favoriteQuestionDao: FavoriteQuestionDao
): ViewModel() {
    private var observeFavoritesUseCase = ObserveFavoritesUseCase(favoriteQuestionDao)

    val favoriteQuestions = observeFavoritesUseCase.observe()
}