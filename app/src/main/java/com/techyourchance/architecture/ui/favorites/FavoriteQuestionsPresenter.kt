package com.techyourchance.architecture.ui.favorites

import com.techyourchance.architecture.common.database.FavoriteQuestionDao

class FavoriteQuestionsPresenter(
    private val favoriteQuestionDao: FavoriteQuestionDao
) {
    val favoriteQuestions by lazy {
        favoriteQuestionDao.observe()
    }
}