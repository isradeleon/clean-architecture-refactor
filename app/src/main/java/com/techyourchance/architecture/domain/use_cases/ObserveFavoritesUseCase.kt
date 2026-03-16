package com.techyourchance.architecture.domain.use_cases

import com.techyourchance.architecture.common.database.daos.FavoriteQuestionDao
import com.techyourchance.architecture.common.database.entities.toQuestionModel
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ObserveFavoritesUseCase @Inject constructor(
    private val favoriteQuestionDao: FavoriteQuestionDao
) {
    fun observe() = favoriteQuestionDao.observe().map { entities ->
        entities.map {
            it.toQuestionModel()
        }
    }
}