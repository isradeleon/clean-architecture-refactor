package com.techyourchance.architecture.common.network.schemas

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.techyourchance.architecture.domain.model.question.Question

@JsonClass(generateAdapter = true)
data class QuestionWithBodySchema(
    @Json(name = "title") val title: String,
    @Json(name = "question_id") val id: String,
    @Json(name = "body") val body: String,
    @Json(name = "owner") val owner: UserSchema,
)

fun QuestionWithBodySchema.toQuestionModel(): Question {
    return Question(
        id = id,
        title = title,
        body = body
    )
}