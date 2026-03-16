package com.techyourchance.architecture.domain.use_cases

import com.techyourchance.architecture.common.database.FavoriteQuestionDao

class ObserveFavoritesUseCase (
    private val favoriteQuestionDao: FavoriteQuestionDao
){
    fun observe() = favoriteQuestionDao.observe()
}