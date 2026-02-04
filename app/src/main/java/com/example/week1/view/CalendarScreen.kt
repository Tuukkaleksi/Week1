package com.example.week1.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.week1.model.Task
import com.example.week1.viewmodel.TaskViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarScreen(
    vm: TaskViewModel,
    onBackToHome: () -> Unit
) {
    val tasks by vm.tasks.collectAsState()
    var selected by remember { mutableStateOf<Task?>(null) }

    // "" -> "No date"
    val grouped = tasks.groupBy { it.dueDate.ifBlank { "No date" } }
        .toSortedMap() // aakkosjärjestys

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Calendar") },
                navigationIcon = {
                    IconButton(onClick = onBackToHome) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            grouped.forEach { (date, dayTasks) ->
                item {
                    Text(date, style = MaterialTheme.typography.titleMedium)
                    Spacer(Modifier.height(8.dp))
                }

                items(dayTasks, key = { it.id }) { task ->
                    Card(Modifier.fillMaxWidth()) {
                        ListItem(
                            headlineContent = { Text(task.title) },
                            supportingContent = { if (task.description.isNotBlank()) Text(task.description) },
                            trailingContent = {
                                Checkbox(
                                    checked = task.done,
                                    onCheckedChange = { vm.toggleDone(task.id) }
                                )
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { selected = task }
                        )
                    }
                    Spacer(Modifier.height(8.dp))
                }
            }
        }
    }

    selected?.let { task ->
        EditTaskDialog(
            task = task,
            onDismiss = { selected = null },
            onSave = { vm.updateTask(it); selected = null },
            onDelete = { vm.removeTask(task.id); selected = null }
        )
    }
}