package com.techyourchance.architecture.ui.question_details

import androidx.lifecycle.ViewModel
import com.techyourchance.architecture.common.database.FavoriteQuestionDao
import com.techyourchance.architecture.common.network.StackoverflowApi
import com.techyourchance.architecture.domain.question.QuestionWithBodySchema
import com.techyourchance.architecture.domain.use_cases.FetchQuestionDetailsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.withContext

class QuestionDetailsViewModel(
    stackoverflowApi: StackoverflowApi,
    favoriteQuestionDao: FavoriteQuestionDao
): ViewModel() {

    val fetchQuestionDetailsUseCase = FetchQuestionDetailsUseCase(
        stackoverflowApi, favoriteQuestionDao
    )

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
            fetchQuestionDetailsUseCase.fetch(questionId)
                .collect { result ->
                    questionDetails.value = result
                }
        }
    }
}