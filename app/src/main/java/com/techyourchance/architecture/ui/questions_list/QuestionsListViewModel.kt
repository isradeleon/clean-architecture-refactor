package com.techyourchance.architecture.ui.questions_list

import androidx.lifecycle.ViewModel
import com.techyourchance.architecture.domain.model.question.Question
import com.techyourchance.architecture.domain.use_cases.FetchQuestionsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class QuestionsListViewModel @Inject constructor(
    private val fetchQuestionsUseCase: FetchQuestionsUseCase
): ViewModel() {
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