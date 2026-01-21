package com.example.week1.ui.theme

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.week1.domain.Task
import com.example.week1.ui.theme.TaskViewModel

@Composable
fun HomeScreen(vm: TaskViewModel = viewModel()) {

    var newTitle by remember { mutableStateOf("") }
    var showOnlyDone by remember { mutableStateOf(false) }

    val tasks = vm.tasks

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(text = "Tehtävälista", fontSize = 26.sp)

        Spacer(Modifier.height(12.dp))

        // Uuden tehtävän lisäys
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = newTitle,
                onValueChange = { newTitle = it },
                modifier = Modifier.weight(1f),
                label = { Text("Uusi tehtävä") },
                singleLine = true
            )

            Button(
                onClick = {
                    val title = newTitle.trim()
                    if (title.isEmpty()) return@Button

                    val nextId = (tasks.maxOfOrNull { it.id } ?: 0) + 1
                    val task = Task(
                        id = nextId,
                        title = title,
                        description = "Lisätty",
                        priority = 1,
                        dueDate = "21-01-2026",
                        done = false
                    )
                    vm.addTask(task)
                    newTitle = ""
                }
            ) { Text("Lisää") }
        }

        Spacer(Modifier.height(8.dp))

        // suodatus + lajittelu
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(
                onClick = {
                    showOnlyDone = !showOnlyDone
                    if (showOnlyDone) vm.filterByDone(done = true) else vm.resetFilter()
                },
                modifier = Modifier.weight(1f)
            ) {
                Text(if (showOnlyDone) "Näytä kaikki" else "Näytä valmiit")
            }

            Button(
                onClick = { vm.sortByDate() },
                modifier = Modifier.weight(1f)
            ) { Text("Järjestä") }
        }

        Spacer(Modifier.height(16.dp))

        if (tasks.isEmpty()) {
            Text("Ei tehtäviä")
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(bottom = 24.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(tasks, key = { it.id }) { task ->
                    TaskRow(
                        task = task,
                        onToggleDone = { vm.toggleDone(task.id) },
                        onRemove = { vm.removeTask(task.id) }
                    )
                }
            }
        }
    }
}

@Composable
private fun TaskRow(
    task: Task,
    onToggleDone: () -> Unit,
    onRemove: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = task.done,
                onCheckedChange = { onToggleDone() }
            )

            Spacer(Modifier.width(8.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(text = task.title, fontSize = 16.sp)
                Text(
                    text = "Määräaika: ${task.dueDate} • Tärkeys: ${task.priority}",
                    fontSize = 12.sp
                )
            }

            TextButton(onClick = onRemove) {
                Text("Poista")
            }
        }
    }
}
