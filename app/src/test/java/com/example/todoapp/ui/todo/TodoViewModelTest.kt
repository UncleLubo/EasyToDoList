package com.example.todoapp.ui.todo

import app.cash.turbine.test
import com.example.todoapp.data.repository.TodoRepository
import com.example.todoapp.domain.model.Todo
import com.example.todoapp.util.MainDispatcherRule
import com.example.todoapp.widget.WidgetUpdater
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class TodoViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var viewModel: TodoViewModel
    private val repository: TodoRepository = mockk(relaxed = true)
    private val widgetUpdater: WidgetUpdater = mockk(relaxed = true)

    private val todoFlow = MutableStateFlow<List<Todo>>(emptyList())

    @Before
    fun setup() {
        coEvery { repository.observeTodos() } returns todoFlow
        viewModel = TodoViewModel(repository, widgetUpdater)
    }

    @Test
    fun `todos state reflects repository data`() = runTest {
        val testData = listOf(Todo(id = 1, title = "Test Task"))
        todoFlow.value = testData

        viewModel.todos.test {
            assertEquals(testData, awaitItem())
        }
    }

    @Test
    fun `addTodo calls repository with trimmed title`() = runTest {
        val title = "  New Task  "
        viewModel.addTodo(title)

        coVerify { repository.insert(match { it.title == "New Task" }) }
        coVerify { widgetUpdater.refresh() }
    }

    @Test
    fun `addTodo ignores empty title`() = runTest {
        viewModel.addTodo("   ")
        coVerify(exactly = 0) { repository.insert(any()) }
    }

    @Test
    fun `toggleTodo updates completion status`() = runTest {
        val todo = Todo(id = 1, title = "Task", isCompleted = false)
        viewModel.toggleTodo(todo)

        coVerify { repository.update(match { it.id == 1 && it.isCompleted }) }
        coVerify { widgetUpdater.refresh() }
    }

    @Test
    fun `deleteTodo removes task`() = runTest {
        val todo = Todo(id = 1, title = "Task")
        viewModel.deleteTodo(todo)

        coVerify { repository.delete(todo) }
        coVerify { widgetUpdater.refresh() }
    }
}
