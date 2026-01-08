package com.example.week1.ui.theme

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.week1.domain.Task

@Composable
fun HomeScreen(tasks: List<Task>) {
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp)
    ) {
        Text(
            text = "Tehtävälista",
            fontSize = 26.sp,
            modifier = Modifier.padding(12.dp)
        )
        // Kortti listalle
        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        ) {
            Column(modifier = Modifier.padding(12.dp)) {
                tasks.forEachIndexed { index, task ->
                    TaskRow(task = task)
                    // viiva rivien väliin
                    if (index != tasks.lastIndex) {
                        HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
                    }
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