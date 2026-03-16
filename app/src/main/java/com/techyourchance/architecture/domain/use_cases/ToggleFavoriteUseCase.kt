package com.techyourchance.architecture.domain.use_cases

import com.techyourchance.architecture.common.database.daos.FavoriteQuestionDao
import com.techyourchance.architecture.common.database.entities.FavoriteEntity
import javax.inject.Inject

class ToggleFavoriteUseCase @Inject constructor(
    private val favoriteQuestionDao: FavoriteQuestionDao
) {
    suspend fun toggle(
        questionId: String,
        questionTitle: String
    ) {
        if (favoriteQuestionDao.getById(questionId) != null) {
            favoriteQuestionDao.delete(questionId)
        } else {
            favoriteQuestionDao.upsert(
                FavoriteEntity(
                    id = questionId,
                    title = questionTitle
                )
            )
        }
    }
}