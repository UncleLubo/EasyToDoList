package com.example.todoapp.data.mapper

import com.example.todoapp.data.local.TodoEntity
import com.example.todoapp.domain.model.Todo
import org.junit.Assert.assertEquals
import org.junit.Test

class TodoMapperTest {

    @Test
    fun `toDomain maps all entity properties correctly`() {
        // Arrange
        val entity = TodoEntity(
            id = 42,
            title = "Buy groceries",
            isCompleted = true,
            createdAt = 1690000000000L,
            position = 5
        )

        // Act
        val domainModel = entity.toDomain()

        // Assert
        assertEquals(42, domainModel.id)
        assertEquals("Buy groceries", domainModel.title)
        assertEquals(true, domainModel.isCompleted)
        assertEquals(1690000000000L, domainModel.createdAt)
        assertEquals(5, domainModel.position)
    }

    @Test
    fun `toEntity maps all domain properties correctly`() {
        // Arrange
        val domainModel = Todo(
            id = 99,
            title = "Walk the dog",
            isCompleted = false,
            createdAt = 1700000000000L,
            position = 2
        )

        // Act
        val entity = domainModel.toEntity()

        // Assert
        assertEquals(99, entity.id)
        assertEquals("Walk the dog", entity.title)
        assertEquals(false, entity.isCompleted)
        assertEquals(1700000000000L, entity.createdAt)
        assertEquals(2, entity.position)
    }
}
