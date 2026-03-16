package com.techyourchance.architecture.domain.use_cases

import com.techyourchance.architecture.common.database.daos.FavoriteQuestionDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class IsQuestionInFavoritesUseCase @Inject constructor(
    private val favoriteQuestionDao: FavoriteQuestionDao
) {
    fun isIt(questionId: String): Flow<Boolean> {
        return favoriteQuestionDao.observeById(questionId).map {
            it != null
        }
    }
}