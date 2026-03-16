package com.techyourchance.architecture.domain.use_cases

import com.techyourchance.architecture.common.network.StackoverflowApi
import com.techyourchance.architecture.common.network.schemas.toQuestionModel
import com.techyourchance.architecture.domain.model.question.Question
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Use cases implement the Single Responsibility Principle,
 * since they represent & encapsulate 1 single application's flow.
 *
 * BTW: They're also called "Interactors" sometimes.
 * */
class FetchQuestionsUseCase @Inject constructor(
    private val stackoverflowApi: StackoverflowApi
) {
    private var questions: List<Question> = emptyList()

    /**
     * Use cases should generally expose only 1 public function.
     * */
    suspend fun fetch(): List<Question> {
        return if (hasEnoughTimePassed())
            withContext(Dispatchers.IO) {
                questions = stackoverflowApi
                    .fetchLastActiveQuestions(20)!!
                    .questions.map {
                        it.toQuestionModel()
                    }
                lastNetworkRequestInNanoS = System.nanoTime()
                questions
            }
        else questions
    }

    private var lastNetworkRequestInNanoS = 0L

    private fun hasEnoughTimePassed(): Boolean {
        return System.nanoTime() - lastNetworkRequestInNanoS > THROTTLE_TIME_OUT
    }

    companion object {
        // Milliseconds to nano seconds conversion
        const val THROTTLE_TIME_OUT = 5000L * 1_000_000L
    }
}