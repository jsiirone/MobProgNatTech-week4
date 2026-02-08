package com.example.mobprognattech_week3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mobprognattech_week3.navigation.ROUTE_CALENDAR
import com.example.mobprognattech_week3.navigation.ROUTE_HOME
import com.example.mobprognattech_week3.navigation.ROUTE_SETTINGS
import com.example.mobprognattech_week3.view.CalendarScreen
import com.example.mobprognattech_week3.view.HomeScreen
import com.example.mobprognattech_week3.view.SettingsScreen
import com.example.mobprognattech_week3.viewmodel.TaskViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val navController = rememberNavController()
            val viewModel: TaskViewModel = viewModel()

            NavHost(
                navController = navController,
                startDestination = ROUTE_HOME
            ) {

                composable(ROUTE_HOME) {
                    HomeScreen(
                        viewModel = viewModel,
                        onTaskClick = { id ->
                            viewModel.openTask(id)
                        },
                        onAddClick = {
                            viewModel.addTaskDialogVisible.value = true
                        },
                        onNavigateCalendar = {
                            navController.navigate(ROUTE_CALENDAR)
                        },
                        onNavigateSettings = {
                            navController.navigate(ROUTE_SETTINGS)
                        }
                    )
                }


                composable(ROUTE_CALENDAR) {
                    CalendarScreen(
                        viewModel = viewModel,
                        onTaskClick = { id ->
                            viewModel.openTask(id)
                        },
                        onNavigateHome = {
                            navController.navigate(ROUTE_HOME)
                        }
                    )
                }
                composable(ROUTE_SETTINGS) {
                    SettingsScreen(
                        onNavigateHome = {
                            navController.navigate(ROUTE_HOME)
                        }
                    )
                }

            }
        }

    }
}