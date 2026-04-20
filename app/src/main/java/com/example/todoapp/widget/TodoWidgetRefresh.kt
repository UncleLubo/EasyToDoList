package com.example.todoapp.widget

import android.content.Context
import android.util.Log
import androidx.glance.appwidget.GlanceAppWidgetManager

/**
 * Triggers a Glance update for every placed widget instance.
 */
object TodoWidgetRefresh {
    private const val TAG = "TodoWidgetRefresh"

    suspend fun refresh(context: Context) {
        try {
            val appContext = context.applicationContext
            val manager = GlanceAppWidgetManager(appContext)
            val glanceIds = manager.getGlanceIds(TodoListGlanceWidget::class.java)
            for (id in glanceIds) {
                TodoListGlanceWidget().update(appContext, id)
            }
        } catch (e: Exception) {
            Log.e(TAG, "Widget refresh failed", e)
        }
    }
}
