package com.example.todoapp.data.repository

import com.example.todoapp.data.local.TodoDao
import com.example.todoapp.data.local.TodoEntity
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.Flow

@Singleton
class TodoRepository @Inject constructor(
    private val todoDao: TodoDao,
) {
    fun observeTodos(): Flow<List<TodoEntity>> = todoDao.getAll()

    suspend fun insert(todo: TodoEntity) {
        todoDao.insert(todo)
    }

    suspend fun update(todo: TodoEntity) {
        todoDao.update(todo)
    }

    suspend fun updateAll(todos: List<TodoEntity>) {
        todoDao.updateAll(todos)
    }

    suspend fun delete(todo: TodoEntity) {
        todoDao.delete(todo)
    }
}
