package com.example.week1.ui.theme

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.week1.domain.Task
import com.example.week1.domain.TaskRepository

class TaskViewModel : ViewModel() {
    var tasks by mutableStateOf(listOf<Task>())
        private set
    private var originalTasks: List<Task> = emptyList();

    init {
        tasks = TaskRepository.mockTasks
        originalTasks = tasks
    }

    fun addTask(task: Task) {
        tasks = tasks + task
        originalTasks = tasks
    }

    fun toggleDone(id: Int) {
        tasks = tasks.map { if (it.id == id) it.copy(done = !it.done) else it }
        originalTasks = tasks
    }

    fun removeTask(id: Int) {
        tasks = tasks.filterNot { it.id == id }
        originalTasks = tasks
    }

    fun filterByDone(done: Boolean) {
        tasks = originalTasks.filter { it.done == done }
    }

    fun resetFilter() {
        tasks = originalTasks
    }

    fun sortByDate() {
        fun key(date: String): String {
            val parts = date.split("-")
            if (parts.size != 3) return ""
            val (dd, mm, yyyy) = parts
            return "\${yyyy.padStart(4, '0')}-\${mm.padStart(2, '0')}-\${dd.padStart(2, '0')}"
        }
        tasks = tasks.sortedBy { key(it.dueDate) }
        originalTasks = tasks
    }
}