package com.example.mobprognattech_week3.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mobprognattech_week3.viewmodel.TaskViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: TaskViewModel = viewModel(),
    onTaskClick: (Int) -> Unit = {},
    onAddClick: () -> Unit = {},
    onNavigateCalendar: () -> Unit = {},
    onNavigateSettings: () -> Unit = {}

) {
    val tasks by viewModel.tasks.collectAsState()
    val selectedTask by viewModel.selectedTask.collectAsState()
    val addTaskFlag by viewModel.addTaskDialogVisible.collectAsState()


    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Tasks", style = MaterialTheme.typography.headlineMedium) },
                actions = {
                    IconButton(onClick = onAddClick) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Create new task"
                        )
                    }
                    IconButton(onClick = onNavigateSettings) {
                        Icon(
                            imageVector = Icons.Default.Settings,
                            contentDescription = "Go to settings"
                        )
                    }
                    IconButton(onClick = onNavigateCalendar) {
                        Icon(
                            imageVector = Icons.Default.CalendarMonth,
                            contentDescription = "Go to calendar"
                        )
                    }

                }
            )
        },
    ) { paddingValues ->


        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
        )

        {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                ElevatedButton(
                    onClick = { viewModel.filterByDone(done = true) }
                ) {
                    Text("Done")
                }

                ElevatedButton(onClick = { viewModel.clearFilter() }) {
                    Text("All")
                }
            }

            LazyColumn() {
                items(tasks) { task ->
                    Card(
                        modifier = Modifier
                            .padding(8.dp)
                            .clickable { onTaskClick(task.id) }
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column {
                                Text(task.title, style = MaterialTheme.typography.headlineSmall)
                                Text(task.description)
                            }
                            Checkbox(
                                checked = task.done,
                                onCheckedChange = { viewModel.toggleDone(task.id) }
                            )
                        }
                    }
                }
            }
        }

        selectedTask?.let { it1 ->
            DetailDialog(
                task = it1!!,
                onClose = { viewModel.closeDialog() },
                onUpdate = { viewModel.updateTask(it) },
                onDelete = { viewModel.removeTask(selectedTask!!.id) })
        }
        if (addTaskFlag) {
            AddDialog(
                onClose = { viewModel.addTaskDialogVisible.value = false },
                onUpdate = { viewModel.addTask(it) }
            )
        }
    }
}
