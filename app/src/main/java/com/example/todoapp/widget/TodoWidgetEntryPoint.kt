package com.example.todoapp.widget

import com.example.todoapp.data.local.TodoDao
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@EntryPoint
@InstallIn(SingletonComponent::class)
interface TodoWidgetEntryPoint {
    fun todoDao(): TodoDao
}
