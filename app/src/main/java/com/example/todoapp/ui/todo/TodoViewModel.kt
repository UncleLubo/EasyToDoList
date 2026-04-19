package com.example.todoapp.ui.todo

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapp.data.local.TodoEntity
import com.example.todoapp.data.repository.TodoRepository
import com.example.todoapp.widget.TodoWidgetRefresh
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@HiltViewModel
class TodoViewModel @Inject constructor(
    private val repository: TodoRepository,
    @ApplicationContext private val context: Context,
) : ViewModel() {

    val todos: StateFlow<List<TodoEntity>> = repository.observeTodos()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyList(),
        )

    private suspend fun refreshWidget() {
        TodoWidgetRefresh.refresh(context)
    }

    /**
     * Adds a todo with the given [title] after trimming surrounding whitespace.
     * Empty or blank titles are ignored and nothing is persisted.
     */
    fun addTodo(title: String) {
        val trimmed = title.trim()
        if (trimmed.isBlank()) return
        viewModelScope.launch {
            repository.insert(TodoEntity(title = trimmed))
            refreshWidget()
        }
    }

    /**
     * Toggles [TodoEntity.isCompleted] for [todo] and saves the updated row.
     */
    fun toggleTodo(todo: TodoEntity) {
        viewModelScope.launch {
            repository.update(todo.copy(isCompleted = !todo.isCompleted))
            refreshWidget()
        }
    }

    /**
     * Permanently removes [todo] from local storage.
     */
    fun deleteTodo(todo: TodoEntity) {
        viewModelScope.launch {
            repository.delete(todo)
            refreshWidget()
        }
    }

    /**
     * Reorders tasks and updates their positions in the database.
     */
    fun saveReorderedTasks(reorderedList: List<TodoEntity>) {
        val updatedList = reorderedList.mapIndexed { index, todo ->
            todo.copy(position = index)
        }

        viewModelScope.launch {
            repository.updateAll(updatedList)
            refreshWidget()
        }
    }
}
