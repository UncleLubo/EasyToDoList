package com.example.todoapp.data.repository

import com.example.todoapp.data.local.TodoDao
import com.example.todoapp.data.mapper.toDomain
import com.example.todoapp.data.mapper.toEntity
import com.example.todoapp.domain.model.Todo
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

@Singleton
class TodoRepository @Inject constructor(
    private val todoDao: TodoDao,
) {
    fun observeTodos(): Flow<List<Todo>> = todoDao.getAll().map { list -> list.map { it.toDomain() } }

    /** One-shot read for the home screen widget (avoids Flow timing vs. DB writes). */
    suspend fun getTodosForWidget(limit: Int): List<Todo> = todoDao.getTodosForWidget(limit).map { it.toDomain() }

    suspend fun insert(todo: Todo) {
        todoDao.insert(todo.toEntity())
    }

    suspend fun update(todo: Todo) {
        todoDao.update(todo.toEntity())
    }

    suspend fun updateAll(todos: List<Todo>) {
        todoDao.updateAll(todos.map { it.toEntity() })
    }

    suspend fun delete(todo: Todo) {
        todoDao.delete(todo.toEntity())
    }
}
