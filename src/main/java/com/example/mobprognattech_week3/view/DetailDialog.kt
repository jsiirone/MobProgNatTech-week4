package com.example.mobprognattech_week3.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.mobprognattech_week3.model.Task


@Composable
fun DetailDialog(
    task: Task,
    onClose: () -> Unit,
    onUpdate: (Task) -> Unit,
    onDelete: (Int) -> Unit
) {
    var title by remember { mutableStateOf(task.title) }
    var description by remember { mutableStateOf(task.description) }

    AlertDialog(
        onDismissRequest = onClose,

        title = { Text("Edit Task") },

        text = {
            Column {
                TextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Title") }
                )
                TextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Description") }
                )
            }
        },

        confirmButton = {
            Button(onClick = {
                onUpdate(task.copy(title = title, description = description))
                onClose()
            }) {
                Text("Save")
            }
        },

        dismissButton = {
            Row {
                TextButton(onClick = {
                    onDelete(task.id)
                    onClose()
                })
                {
                    Text("Delete")
                }
                Spacer(modifier = Modifier.width(8.dp))
                TextButton(onClick = onClose) {
                    Text("Cancel")
                }
            }
        }
    )
}
