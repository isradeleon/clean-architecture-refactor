package com.techyourchance.architecture.ui.questions_list

import androidx.lifecycle.ViewModel
import com.techyourchance.architecture.domain.model.question.Question
import com.techyourchance.architecture.domain.use_cases.FetchQuestionsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.withContext

class QuestionsListViewModel: ViewModel() {
    val fetchQuestionsUseCase = FetchQuestionsUseCase()

    val questions = MutableStateFlow<List<Question>>(emptyList())

    suspend fun fetchQuestions(
        forceUpdate: Boolean = false
    ) {
        if (forceUpdate || questions.value.isEmpty())
            withContext(Dispatchers.Main.immediate) {
                questions.value = fetchQuestionsUseCase.fetch()
            }
    }
}