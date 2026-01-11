package com.example.week1.ui.theme

import android.widget.Space
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.week1.domain.*

@Composable
fun HomeScreen(initialTasks: List<Task>) {
    
    var tasks by remember { mutableStateOf(initialTasks) }
    var showOnlyDone by remember { mutableStateOf(false) }
    
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp)
    ) {
        Text(text = "Tehtävälista", fontSize = 26.sp)

        Spacer(Modifier.height(12.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(
                onClick = {
                    val nextId = (tasks.maxOfOrNull { it.id } ?: 0) + 1
                    val newTask = Task(
                        id = nextId,
                        title = "Uusi tehtävä $nextId",
                        description = "Lisätty",
                        priority = 1,
                        dueDate = "20-01-2026",
                        done = false
                    )
                    tasks = addTask(tasks, newTask)
                },
                modifier = Modifier.weight(1f)
            ) { Text("Lisää") }
            Button(
                onClick = {
                    //toggletaan listan ensimmäinen
                    val firstId = tasks.firstOrNull()?.id ?: return@Button
                    tasks = toggleDone(tasks, firstId)
                },
                modifier = Modifier.weight(1f)
            ) { Text("Toggle") }
        }

        Spacer(Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(
                onClick = {
                    showOnlyDone = !showOnlyDone
                },
                modifier = Modifier.weight(1f)
            ) { Text(if (showOnlyDone) "Näytä kaikki" else "Näytä valmiit") }

            Button(
                onClick = {
                    tasks = sortByDueDate(tasks, done = true)
                },
                modifier = Modifier.weight(1f)
            ) { Text("Järjestys") }
        }

        Spacer(Modifier.height(16.dp))

        val visibleTasks = if (showOnlyDone) filterByDone(tasks, done = true) else tasks

        if (visibleTasks.isEmpty()) {
            Text("Ei tehtäviä")
        } else {
            visibleTasks.forEachIndexed { index, task ->
                TaskRow(task)
                if (index != visibleTasks.lastIndex) {
                    HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
                }
            }
        }
    }
}

@Composable
private fun TaskRow(task: Task) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = (if (task.done) "✔ " else "X ") + task.title,
                fontSize = 16.sp
            )
            Text(
                text = "Määräaika: ${task.dueDate} " + "Tärkeys: ${task.priority}",
                fontSize = 12.sp
            )
        }
    }
}