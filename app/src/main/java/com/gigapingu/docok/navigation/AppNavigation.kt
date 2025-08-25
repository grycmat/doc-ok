package com.gigapingu.docok.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.gigapingu.docok.ui.screens.NewAppointmentScreen

@Composable
fun AppNavigation(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(navController = navController, startDestination = "new_appointment") {
        composable("new_appointment") {
            NewAppointmentScreen(
                onNavigateToRecording = { /* TODO */ },
                onNavigateBack = { /* TODO */ }
            )
        }
    }
}
