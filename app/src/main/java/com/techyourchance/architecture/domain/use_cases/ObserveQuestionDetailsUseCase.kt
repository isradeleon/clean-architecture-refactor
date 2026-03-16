package com.techyourchance.architecture.domain.use_cases

import com.techyourchance.architecture.common.database.daos.FavoriteQuestionDao
import com.techyourchance.architecture.common.network.StackoverflowApi
import com.techyourchance.architecture.common.network.schemas.toQuestionModel
import com.techyourchance.architecture.domain.model.question.QuestionDetailsWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ObserveQuestionDetailsUseCase @Inject constructor(
    private val stackoverflowApi: StackoverflowApi,
    private val favoriteQuestionDao: FavoriteQuestionDao
) {
    suspend fun observe(questionId: String): Flow<QuestionDetailsWrapper?> {
        return withContext(Dispatchers.IO) {
            combine(
                flow = flow {
                    emit(
                        stackoverflowApi.fetchQuestionDetails(questionId)
                    )
                },
                flow2 = favoriteQuestionDao.observeById(questionId)
            ) { details, favoriteQuestion ->
                if (details != null && details.questions.isNotEmpty()) {
                    QuestionDetailsWrapper(
                        details = details.questions[0].toQuestionModel(),
                        isFavorite = favoriteQuestion != null
                    )
                } else {
                    null
                }
            }.catch {
                emit(null)
            }
        }
    }
}