package com.techyourchance.architecture.common.network.schemas

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.techyourchance.architecture.domain.question.Question

@JsonClass(generateAdapter = true)
data class QuestionSchema(
    @Json(name = "title") val title: String,
    @Json(name = "question_id") val id: String,
    @Json(name = "owner") val owner: UserSchema,
)

fun QuestionSchema.toQuestionModel(): Question {
    return Question(
        id = id,
        title = title
    )
}