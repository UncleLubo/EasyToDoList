package com.example.todoapp.data.repository

import android.content.Context
import com.example.todoapp.data.local.TodoDao
import com.example.todoapp.data.local.TodoEntity
import com.example.todoapp.widget.TodoWidgetRefresh
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.Flow

@Singleton
class TodoRepository @Inject constructor(
    private val todoDao: TodoDao,
    @ApplicationContext private val context: Context,
) {
    fun observeTodos(): Flow<List<TodoEntity>> = todoDao.getAll()

    suspend fun insert(todo: TodoEntity) {
        todoDao.insert(todo)
        TodoWidgetRefresh.refresh(context)
    }

    suspend fun update(todo: TodoEntity) {
        todoDao.update(todo)
        TodoWidgetRefresh.refresh(context)
    }

    suspend fun delete(todo: TodoEntity) {
        todoDao.delete(todo)
        TodoWidgetRefresh.refresh(context)
    }
}
