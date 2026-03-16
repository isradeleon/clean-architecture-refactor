package com.techyourchance.architecture.domain.model.question

data class Question(
    val id: String,
    val title: String,
    val body: String = ""
)
