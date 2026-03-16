package com.techyourchance.architecture.ui.question_details

import androidx.lifecycle.ViewModel
import com.techyourchance.architecture.domain.model.question.Question
import com.techyourchance.architecture.domain.use_cases.ObserveQuestionDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class QuestionDetailsViewModel @Inject constructor(
    private val observeQuestionDetailsUseCase: ObserveQuestionDetailsUseCase
): ViewModel() {

    val questionDetails = MutableStateFlow<QuestionDetailsResult>(QuestionDetailsResult.None)

    sealed class QuestionDetailsResult {
        data object None: QuestionDetailsResult()
        data class Success(
            val questionDetails: Question,
            val isFavorite: Boolean
        ): QuestionDetailsResult()
        data object Error: QuestionDetailsResult()
    }

    suspend fun fetchDetails(questionId: String) {
        withContext(Dispatchers.Main.immediate) {
            observeQuestionDetailsUseCase.observe(questionId)
                .collect { result ->
                    result?.let {
                        questionDetails.value = QuestionDetailsResult.Success(
                            questionDetails = result.details,
                            isFavorite = result.isFavorite
                        )
                    } ?: run {
                        questionDetails.value = QuestionDetailsResult.Error
                    }
                }
        }
    }
}