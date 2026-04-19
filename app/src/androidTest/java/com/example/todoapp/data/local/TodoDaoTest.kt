package com.example.todoapp.data.local

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TodoDaoTest {

    private lateinit var database: TodoDatabase
    private lateinit var dao: TodoDao

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(context, TodoDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        dao = database.todoDao()
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun insertAndGetAll_returnsTasksInOrder() = runTest {
        val task1 = TodoEntity(id = 1, title = "First", position = 1)
        val task2 = TodoEntity(id = 2, title = "Second", position = 0)

        dao.insert(task1)
        dao.insert(task2)

        val allTasks = dao.getAll().first()

        assertEquals(2, allTasks.size)
        // Check order (position 0 comes first)
        assertEquals(2, allTasks[0].id)
        assertEquals(1, allTasks[1].id)
    }

    @Test
    fun update_changesTaskTitle() = runTest {
        val task = TodoEntity(id = 1, title = "Old Title")
        dao.insert(task)

        val updatedTask = task.copy(title = "New Title")
        dao.update(updatedTask)

        val allTasks = dao.getAll().first()
        assertEquals("New Title", allTasks[0].title)
    }

    @Test
    fun delete_removesTaskFromDatabase() = runTest {
        val task = TodoEntity(id = 1, title = "Delete Me")
        dao.insert(task)
        dao.delete(task)

        val allTasks = dao.getAll().first()
        assertTrue(allTasks.isEmpty())
    }

    @Test
    fun getTodosForWidget_returnsLimitedList() = runTest {
        for (i in 1..10) {
            dao.insert(TodoEntity(id = i, title = "Task $i", position = i))
        }

        val widgetTasks = dao.getTodosForWidget(5)
        assertEquals(5, widgetTasks.size)
        assertEquals(1, widgetTasks[0].id)
    }

    @Test
    fun updateAll_updatesMultipleTasksAtOnce() = runTest {
        val task1 = TodoEntity(id = 1, title = "Task 1", position = 0)
        val task2 = TodoEntity(id = 2, title = "Task 2", position = 1)
        dao.insert(task1)
        dao.insert(task2)

        val updatedTask1 = task1.copy(position = 1)
        val updatedTask2 = task2.copy(position = 0)
        dao.updateAll(listOf(updatedTask1, updatedTask2))

        val allTasks = dao.getAll().first()

        assertEquals(2, allTasks.size)
        assertEquals(2, allTasks[0].id)
        assertEquals(0, allTasks[0].position)

        assertEquals(1, allTasks[1].id)
        assertEquals(1, allTasks[1].position)
    }
}
