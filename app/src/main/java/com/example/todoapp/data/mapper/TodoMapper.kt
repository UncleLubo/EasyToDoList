package com.example.todoapp.data.mapper

import com.example.todoapp.data.local.TodoEntity
import com.example.todoapp.domain.model.Todo

fun TodoEntity.toDomain(): Todo {
    return Todo(
        id = id,
        title = title,
        isCompleted = isCompleted,
        createdAt = createdAt,
        position = position
    )
}

fun Todo.toEntity(): TodoEntity {
    return TodoEntity(
        id = id,
        title = title,
        isCompleted = isCompleted,
        createdAt = createdAt,
        position = position
    )
}
