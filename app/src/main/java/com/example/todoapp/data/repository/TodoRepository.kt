package com.example.todoapp.data.repository

import com.example.todoapp.domain.model.Todo
import kotlinx.coroutines.flow.Flow

interface TodoRepository {
    fun observeTodos(): Flow<List<Todo>>

    fun observeTodos(limit: Int): Flow<List<Todo>>
    
    /** One-shot read for the home screen widget (avoids Flow timing vs. DB writes). */
    suspend fun getTodosForWidget(limit: Int): List<Todo>
    
    suspend fun insert(todo: Todo)
    
    suspend fun update(todo: Todo)
    
    suspend fun updateAll(todos: List<Todo>)
    
    suspend fun delete(todo: Todo)
}
