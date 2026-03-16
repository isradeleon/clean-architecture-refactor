package com.techyourchance.architecture.domain.question

data class Question(
    val id: String,
    val title: String,
    val body: String = ""
)
