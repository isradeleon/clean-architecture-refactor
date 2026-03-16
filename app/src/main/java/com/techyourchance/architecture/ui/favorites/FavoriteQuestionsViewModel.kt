package com.techyourchance.architecture.ui.favorites

import androidx.lifecycle.ViewModel
import com.techyourchance.architecture.domain.use_cases.ObserveFavoritesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FavoriteQuestionsViewModel @Inject constructor(
    observeFavoritesUseCase: ObserveFavoritesUseCase
): ViewModel() {
    val favoriteQuestions = observeFavoritesUseCase.observe()
}