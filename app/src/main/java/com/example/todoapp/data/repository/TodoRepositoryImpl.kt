package com.example.todoapp.data.repository

import com.example.todoapp.data.local.TodoDao
import com.example.todoapp.data.local.TodoEntity
import com.example.todoapp.domain.model.Todo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TodoRepositoryImpl @Inject constructor(
    private val todoDao: TodoDao
) : TodoRepository {

    override fun observeTodos(): Flow<List<Todo>> {
        return todoDao.getAll().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override suspend fun getTodosForWidget(limit: Int): List<Todo> {
        return todoDao.getTodosForWidget(limit).map { it.toDomain() }
    }

    override suspend fun insert(todo: Todo) {
        todoDao.insert(todo.toEntity())
    }

    override suspend fun update(todo: Todo) {
        todoDao.update(todo.toEntity())
    }

    override suspend fun updateAll(todos: List<Todo>) {
        todoDao.updateAll(todos.map { it.toEntity() })
    }

    override suspend fun delete(todo: Todo) {
        todoDao.delete(todo.toEntity())
    }
}

fun TodoEntity.toDomain() = Todo(
    id = id,
    title = title,
    isCompleted = isCompleted,
    createdAt = createdAt,
    position = position
)

fun Todo.toEntity() = TodoEntity(
    id = id,
    title = title,
    isCompleted = isCompleted,
    createdAt = createdAt,
    position = position
)
