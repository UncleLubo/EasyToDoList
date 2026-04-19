package com.example.todoapp.domain.model

data class Todo(
    val id: Int = 0,
    val title: String,
    val isCompleted: Boolean = false,
    val createdAt: Long = System.currentTimeMillis(),
    val position: Int = 0,
)
