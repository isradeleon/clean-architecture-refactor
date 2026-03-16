package com.techyourchance.architecture.domain.model.question

data class QuestionDetailsWrapper(
    val details: Question,
    val isFavorite: Boolean
)
