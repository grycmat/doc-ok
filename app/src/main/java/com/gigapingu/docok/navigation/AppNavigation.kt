package com.gigapingu.docok.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.gigapingu.docok.ui.screens.NewAppointmentScreen
import com.gigapingu.docok.ui.screens.RecordScreen

@Composable
fun AppNavigation(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(navController = navController, startDestination = "new_appointment") {
        composable("new_appointment") {
            NewAppointmentScreen(
                onNavigateToRecording = { navController.navigate("recording") },
                onNavigateBack = { /* TODO */ }
            )
        }
        composable("recording") {
            RecordScreen(patientName = "John Doe", medicalRecordNumber = "123456", appointmentType = "General Checkup")
        }
    }
}
