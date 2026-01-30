package com.example.mobprognattech_week3.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
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
fun HomeScreen(viewModel: TaskViewModel = viewModel()) {
    val tasks by viewModel.tasks.collectAsState()
    val selectedTask by viewModel.selectedTask.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Tasks", style = MaterialTheme.typography.headlineMedium) },
                actions = {
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
                            .clickable { viewModel.selectTask(task) }) {
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
    }
}
