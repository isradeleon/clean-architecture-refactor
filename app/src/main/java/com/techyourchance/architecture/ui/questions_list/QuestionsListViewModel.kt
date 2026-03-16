package com.techyourchance.architecture.ui.questions_list

import androidx.lifecycle.ViewModel
import com.techyourchance.architecture.BuildConfig
import com.techyourchance.architecture.common.network.StackoverflowApi
import com.techyourchance.architecture.domain.question.QuestionSchema
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class QuestionsListViewModel: ViewModel() {
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

    val questions = MutableStateFlow<List<QuestionSchema>>(emptyList())

    suspend fun fetchQuestions() {
        withContext(Dispatchers.Main.immediate) {
            questions.value = stackoverflowApi.fetchLastActiveQuestions(20)!!.questions
        }
    }
}