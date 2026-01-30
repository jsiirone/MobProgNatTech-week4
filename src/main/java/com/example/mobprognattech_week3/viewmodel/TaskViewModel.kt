package com.example.mobprognattech_week3.viewmodel

import androidx.lifecycle.ViewModel
import com.example.mobprognattech_week3.model.Task
import com.example.mobprognattech_week3.model.mockTasks
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class TaskViewModel : ViewModel() {

    private val _allTasks = MutableStateFlow<List<Task>>(mockTasks)

    private val _tasks = MutableStateFlow<List<Task>>(mockTasks)
    val tasks: StateFlow<List<Task>> = _tasks.asStateFlow()

    private val _selectedTask = MutableStateFlow<Task?>(null)
    val selectedTask: StateFlow<Task?> = _selectedTask.asStateFlow()


    fun addTask(task: Task) {
        _allTasks.value = _allTasks.value + task
        _tasks.value = _allTasks.value
    }

    fun toggleDone(id: Int) {
        _allTasks.value = _allTasks.value.map {
            if (it.id == id) it.copy(done = !it.done) else it
        }
        _tasks.value = _allTasks.value
    }

    fun removeTask(id: Int) {
        _allTasks.value = _allTasks.value.filter { it.id != id }
        _tasks.value = _allTasks.value
    }

    fun filterByDone(done: Boolean) {
        _tasks.value = _allTasks.value.filter { it.done == done }
    }

    fun clearFilter() {
        _tasks.value = _allTasks.value
    }

    fun selectTask(task: Task) {
        _selectedTask.value = task
    }

    fun updateTask(updated: Task) {
        _allTasks.value = _allTasks.value.map { if (it.id == updated.id) updated else it }
        _tasks.value = _allTasks.value
        _selectedTask.value = null
    }

    fun closeDialog() {
        _selectedTask.value = null
    }
}

