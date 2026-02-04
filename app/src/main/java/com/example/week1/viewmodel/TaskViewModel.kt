package com.example.week1.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.week1.domain.TaskRepository
import com.example.week1.model.Task
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlin.collections.plus

class TaskViewModel : ViewModel() {
    private val _tasks = MutableStateFlow<List<Task>>(emptyList())
    val tasks: StateFlow<List<Task>> = _tasks.asStateFlow()

    private var nextId = 1

    init {
        _tasks.value = TaskRepository.mockTasks
        nextId = (_tasks.value.maxOfOrNull { it.id } ?: 0) + 1
    }

    fun addTask(title: String, description: String, dueDate: String) {
        val newTask = Task(
            id = nextId++,
            title = title.trim(),
            description = description.trim(),
            priority = 1,
            dueDate = dueDate.trim(),
            done = false
        )
        _tasks.update { it + newTask }
    }

    fun toggleDone(id: Int) {
        _tasks.update { list -> list.map { if (it.id == id) it.copy(done = !it.done) else it } }
    }

    fun removeTask(id: Int) {
        _tasks.update { list -> list.filterNot { it.id == id } }
    }

    fun updateTask(updated: Task) {
        _tasks.update { list -> list.map { if (it.id == updated.id) updated else it } }
    }
}