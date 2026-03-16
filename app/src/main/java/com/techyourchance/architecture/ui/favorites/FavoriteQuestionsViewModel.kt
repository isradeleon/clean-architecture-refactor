package com.techyourchance.architecture.ui.favorites

import androidx.lifecycle.ViewModel
import com.techyourchance.architecture.common.database.FavoriteQuestionDao

class FavoriteQuestionsViewModel(
    private val favoriteQuestionDao: FavoriteQuestionDao
): ViewModel() {
    val favoriteQuestions by lazy {
        favoriteQuestionDao.observe()
    }
}