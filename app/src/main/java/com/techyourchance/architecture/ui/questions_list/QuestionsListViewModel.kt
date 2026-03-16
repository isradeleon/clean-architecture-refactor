package com.techyourchance.architecture.ui.questions_list

import androidx.lifecycle.ViewModel
import com.techyourchance.architecture.domain.question.QuestionSchema
import com.techyourchance.architecture.domain.use_cases.FetchQuestionsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.withContext

class QuestionsListViewModel: ViewModel() {
    val fetchQuestionsUseCase = FetchQuestionsUseCase()

    val questions = MutableStateFlow<List<QuestionSchema>>(emptyList())

    suspend fun fetchQuestions() {
        withContext(Dispatchers.Main.immediate) {
            questions.value = fetchQuestionsUseCase.fetch()
        }
    }
}