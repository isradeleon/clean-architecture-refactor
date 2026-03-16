package com.techyourchance.architecture.domain.question

data class QuestionDetailsWrapper(
    val details: QuestionWithBodySchema,
    val isFavorite: Boolean
)
