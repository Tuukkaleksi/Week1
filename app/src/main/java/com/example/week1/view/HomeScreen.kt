package com.example.week1.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.week1.model.Task
import com.example.week1.viewmodel.TaskViewModel
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape

@Composable
fun HomeScreen(vm: TaskViewModel) {
    val tasks by vm.tasks.collectAsState()

    var selected by remember { mutableStateOf<Task?>(null) }

    Column(Modifier.fillMaxSize().padding(16.dp)) {
        Spacer(Modifier.height(12.dp))

        Text("Task List", style = MaterialTheme.typography.headlineSmall)
        Spacer(Modifier.height(12.dp))

        AddTaskRow { title, desc -> vm.addTask(title, desc) }

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(tasks, key = { it.id }) { task ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                ) {
                    ListItem(
                        headlineContent = { Text(task.title) },
                        supportingContent = { Text(task.description) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { selected = task },
                        trailingContent = {
                            Checkbox(
                                checked = task.done,
                                onCheckedChange = { vm.toggleDone(task.id) }
                            )
                        }
                    )
                }
            }
        }
    }

    // Dialogi aukeaa kun selected != null
    selected?.let { task ->
        EditTaskDialog(
            task = task,
            onDismiss = { selected = null },
            onSave = { vm.updateTask(it); selected = null },
        )
    }
}

@Composable
private fun AddTaskRow(onAdd: (String, String) -> Unit) {
    var title by remember { mutableStateOf("") }
    var desc by remember { mutableStateOf("") }

    Column {
        OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Otsikko") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(
            value = desc,
            onValueChange = { desc = it },
            label = { Text("Kuvaus") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(8.dp))
        Button(
            onClick = {
                if (title.isNotBlank()) {
                    onAdd(title, desc)
                    title = ""
                    desc = ""
                }
            }
        ) { Text("Lisää") }
    }
}

@Composable
fun EditTaskDialog(
    task: Task,
    onDismiss: () -> Unit,
    onSave: (Task) -> Unit
) {
    var title by remember(task.id) { mutableStateOf(task.title) }
    var desc by remember(task.id) { mutableStateOf(task.description) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Edit Task") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Title") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = desc,
                    onValueChange = { desc = it },
                    label = { Text("Description") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(onClick = { onSave(task.copy(title = title, description = desc)) }) {
                Text("Save")
            }
        },
        dismissButton = {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                TextButton(onClick = onDismiss) { Text("Cancel") }
            }
        }
    )
}