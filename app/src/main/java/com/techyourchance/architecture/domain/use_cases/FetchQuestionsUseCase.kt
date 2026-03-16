package com.techyourchance.architecture.domain.use_cases

import com.techyourchance.architecture.BuildConfig
import com.techyourchance.architecture.common.network.StackoverflowApi
import com.techyourchance.architecture.common.network.schemas.toQuestionModel
import com.techyourchance.architecture.domain.model.question.Question
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

/**
 * Use cases implement the Single Responsibility Principle,
 * since they represent & encapsulate 1 single application's flow.
 *
 * BTW: They're also called "Interactors" sometimes.
 * */
class FetchQuestionsUseCase {
    private val retrofit by lazy {
        val httpClient = OkHttpClient.Builder().run {
            addInterceptor(HttpLoggingInterceptor().apply {
                if (BuildConfig.DEBUG) {
                    level = HttpLoggingInterceptor.Level.BODY
                }
            })
            build()
        }

        Retrofit.Builder()
            .baseUrl("http://api.stackexchange.com/2.3/")
            .addConverterFactory(MoshiConverterFactory.create())
            .client(httpClient)
            .build()
    }

    private val stackoverflowApi by lazy {
        retrofit.create(StackoverflowApi::class.java)
    }

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