package com.techyourchance.architecture.ui.question_details

import androidx.lifecycle.ViewModel
import com.techyourchance.architecture.common.database.FavoriteQuestionDao
import com.techyourchance.architecture.common.network.StackoverflowApi
import com.techyourchance.architecture.domain.question.QuestionWithBodySchema
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext

class QuestionDetailsViewModel(
    private val stackoverflowApi: StackoverflowApi,
    private val favoriteQuestionDao: FavoriteQuestionDao
): ViewModel() {

    val questionDetails = MutableStateFlow<QuestionDetailsResult>(QuestionDetailsResult.None)

    sealed class QuestionDetailsResult {
        data object None: QuestionDetailsResult()
        data class Success(
            val questionDetails: QuestionWithBodySchema,
            val isFavorite: Boolean
        ): QuestionDetailsResult()
        data object Error: QuestionDetailsResult()
    }

    suspend fun fetchDetails(questionId: String) {
        withContext(Dispatchers.Main.immediate) {
            combine(
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
                questionDetails.value = QuestionDetailsResult.Error
            }.collect { result ->
                questionDetails.value = result
            }
        }
    }
}