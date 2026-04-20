package com.example.todoapp.widget

import android.content.Context
import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.GlanceTheme
import androidx.glance.action.clickable
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.action.actionStartActivity
import androidx.glance.appwidget.provideContent
import androidx.glance.layout.Column
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.padding
import androidx.glance.text.FontWeight
import androidx.glance.text.Text
import androidx.glance.text.TextStyle
import androidx.glance.unit.ColorProvider
import com.example.todoapp.MainActivity
import com.example.todoapp.R
import com.example.todoapp.domain.model.Todo
import com.example.todoapp.data.mapper.toDomain
import dagger.hilt.android.EntryPointAccessors
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TodoListGlanceWidget : GlanceAppWidget() {

    override suspend fun provideGlance(context: Context, id: GlanceId) {
        val repository = EntryPointAccessors.fromApplication(
            context.applicationContext,
            TodoWidgetEntryPoint::class.java,
        ).todoRepository()

        val initialTodos = withContext(Dispatchers.IO) {
            repository.getTodosForWidget(MAX_ITEMS)
        }

        val openApp = Intent(context, MainActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP)
        }

        provideContent {
            val todos by repository.observeTodos(MAX_ITEMS).collectAsState(initial = initialTodos)

            GlanceTheme {
                Column(
                    modifier = GlanceModifier
                        .fillMaxSize()
                        .padding(12.dp)
                        .clickable(actionStartActivity(openApp)),
                ) {
                    Text(
                        text = context.getString(R.string.widget_title),
                        style = TextStyle(fontWeight = FontWeight.Bold),
                    )
                    if (todos.isEmpty()) {
                        Text(text = context.getString(R.string.widget_empty))
                    } else {
                        todos.forEach { todo ->
                            TodoLine(todo)
                        }
                    }
                }
            }
        }
    }

    companion object {
        private const val MAX_ITEMS = 8
    }
}

@Composable
private fun TodoLine(todo: Todo) {
    val prefix = if (todo.isCompleted) "✓ " else "• "
    Text(
        text = prefix + todo.title,
        style = TextStyle(
            color = if (todo.isCompleted) {
                ColorProvider(Color(0xFF757575))
            } else {
                ColorProvider(Color(0xFF1C1B1F))
            },
        ),
        maxLines = 1,
    )
}
