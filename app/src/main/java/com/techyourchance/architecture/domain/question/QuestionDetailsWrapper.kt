package com.techyourchance.architecture.domain.question

data class QuestionDetailsWrapper(
    val details: Question,
    val isFavorite: Boolean
)
