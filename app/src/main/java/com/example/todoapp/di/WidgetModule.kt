package com.example.todoapp.di

import com.example.todoapp.widget.GlanceWidgetUpdater
import com.example.todoapp.widget.WidgetUpdater
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class WidgetModule {
    @Binds
    abstract fun bindWidgetUpdater(
        glanceWidgetUpdater: GlanceWidgetUpdater
    ): WidgetUpdater
}
