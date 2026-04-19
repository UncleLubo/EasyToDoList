package com.example.todoapp.ui.todo

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.todoapp.domain.model.Todo
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TodoScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun emptyState_displaysEmptyStateMessage() {
        composeTestRule.setContent {
            TodoScreenContent(
                todos = emptyList(),
                onAddTodo = {},
                onToggleTodo = {},
                onDeleteTodo = {},
                onMoveTodo = {}
            )
        }

        composeTestRule.onNodeWithText("No todos yet").assertIsDisplayed()
        composeTestRule.onNodeWithText("Tap + to add your first task").assertIsDisplayed()
    }

    @Test
    fun populatedList_displaysTasks() {
        val testTodos = listOf(
            Todo(id = 1, title = "Buy groceries", isCompleted = false),
            Todo(id = 2, title = "Walk the dog", isCompleted = true)
        )

        composeTestRule.setContent {
            TodoScreenContent(
                todos = testTodos,
                onAddTodo = {},
                onToggleTodo = {},
                onDeleteTodo = {},
                onMoveTodo = {}
            )
        }

        composeTestRule.onNodeWithText("Buy groceries").assertIsDisplayed()
        composeTestRule.onNodeWithText("Walk the dog").assertIsDisplayed()
    }

    @Test
    fun addTodo_triggersCallbackWithCorrectText() {
        var addedTodoTitle: String? = null

        composeTestRule.setContent {
            TodoScreenContent(
                todos = emptyList(),
                onAddTodo = { addedTodoTitle = it },
                onToggleTodo = {},
                onDeleteTodo = {},
                onMoveTodo = {}
            )
        }

        // Type in the text field
        composeTestRule.onNodeWithText("What do you need to do?").performTextInput("New Test Task")
        
        // Click the add button
        composeTestRule.onNodeWithContentDescription("Add todo").performClick()

        // Assert the callback was triggered with the correct text
        assert(addedTodoTitle == "New Test Task")
    }
}
