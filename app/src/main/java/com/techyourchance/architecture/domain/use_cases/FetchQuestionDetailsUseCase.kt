package com.techyourchance.architecture.domain.use_cases

import com.techyourchance.architecture.common.database.FavoriteQuestionDao
import com.techyourchance.architecture.common.network.StackoverflowApi
import com.techyourchance.architecture.ui.question_details.QuestionDetailsViewModel.QuestionDetailsResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow

class FetchQuestionDetailsUseCase(
    private val stackoverflowApi: StackoverflowApi,
    private val favoriteQuestionDao: FavoriteQuestionDao
) {
    fun fetch(questionId: String): Flow<QuestionDetailsResult> {
        return combine(
            flow = flow {
                emit(
                    stackoverflowApi.fetchQuestionDetails(questionId)
                )
            },
            flow2 = favoriteQuestionDao.observeById(questionId)
        ) { details, favoriteQuestion ->
            if (details != null && details.questions.isNotEmpty()) {
                QuestionDetailsResult.Success(
                    questionDetails = details.questions[0],
                    isFavorite = favoriteQuestion != null
                )
            } else {
                QuestionDetailsResult.Error
            }
        }.catch {
            emit(
                QuestionDetailsResult.Error
            )
        }
    }
}