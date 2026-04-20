package com.example.todoapp.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoDao {
    @Query("SELECT * FROM todos ORDER BY position ASC, createdAt DESC")
    fun getAll(): Flow<List<TodoEntity>>

    /** Flow read for the home screen widget to stay updated automatically. */
    @Query("SELECT * FROM todos ORDER BY position ASC, createdAt DESC LIMIT :limit")
    fun getTodosForWidgetFlow(limit: Int): Flow<List<TodoEntity>>

    /** One-shot read for the home screen widget (avoids Flow timing vs. DB writes). */
    @Query("SELECT * FROM todos ORDER BY position ASC, createdAt DESC LIMIT :limit")
    suspend fun getTodosForWidget(limit: Int): List<TodoEntity>

    @Insert
    suspend fun insert(todo: TodoEntity)

    @Update
    suspend fun update(todo: TodoEntity)

    @Update
    suspend fun updateAll(todos: List<TodoEntity>)

    @Delete
    suspend fun delete(todo: TodoEntity)
}
