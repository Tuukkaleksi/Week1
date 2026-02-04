package com.example.week1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.week1.navigation.ROUTE_CALENDAR
import com.example.week1.navigation.ROUTE_HOME
import com.example.week1.view.CalendarScreen
import com.example.week1.view.HomeScreen
import com.example.week1.viewmodel.TaskViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {
                AppNav()
            }
        }
    }
}

@Composable
private fun AppNav() {
    val navController = rememberNavController()

    val vm: TaskViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = ROUTE_HOME
    ) {
        composable(route = ROUTE_HOME) {
            HomeScreen(
                vm = vm,
                onOpenCalendar = { navController.navigate(ROUTE_CALENDAR) }
            )
        }

        composable(route = ROUTE_CALENDAR) {
            CalendarScreen(
                vm = vm,
                onBackToHome = { navController.popBackStack() }
            )
        }
    }
}