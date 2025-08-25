package com.gigapingu.docok.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.gigapingu.docok.ui.components.*
import com.gigapingu.docok.ui.theme.*
import java.time.LocalDate
import java.time.LocalTime

@Composable
fun NewAppointmentScreen(
    onNavigateToRecording: (AppointmentData) -> Unit,
    onNavigateBack: () -> Unit,
    viewModel: NewAppointmentViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val scrollState = rememberScrollState()
    val toastState = rememberToastState()
    
    // Date and Time pickers
    var showDatePicker by remember { mutableStateOf(false) }
    var showTimePicker by remember { mutableStateOf(false) }
    
    DocOkTheme {
        GradientBackground {
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(scrollState)
                        .padding(horizontal = 20.dp)
                        .navigationBarsPadding()
                ) {
                    Spacer(modifier = Modifier.height(40.dp))
                    
                    // Header Card
                    HeaderCard(
                        userName = "Dr. Smith",
                        subtitle = if (uiState.progress == 1f) {
                            "Ready to record!"
                        } else {
                            "${uiState.pendingAppointments} appointments pending"
                        },
                        pendingCount = uiState.pendingAppointments
                    )
                    
                    Spacer(modifier = Modifier.height(20.dp))
                    
                    // Main Form Card
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(20.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surface
                        ),
                        elevation = CardDefaults.cardElevation(
                            defaultElevation = 10.dp
                        )
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(24.dp)
                        ) {
                            // Patient Name Search Input
                            SearchInput(
                                value = uiState.patientName,
                                onValueChange = { viewModel.updatePatientName(it) },
                                placeholder = "Patient name...",
                                onSearchClick = {
                                    // Optional: Open patient search
                                }
                            )
                            
                            Spacer(modifier = Modifier.height(24.dp))
                            
                            // Appointment Type Section
                            SectionTitle(
                                title = "Appointment Type",
                                showSeeAll = true,
                                onSeeAllClick = {
                                    // Show all appointment types
                                }
                            )
                            
                            Spacer(modifier = Modifier.height(16.dp))
                            
                            CategoryGrid(
                                categories = listOf(
                                    CategoryItem(
                                        id = "consultation",
                                        emoji = "🩺",
                                        title = "Consultation",
                                        subtitle = "30-45 min",
                                        color = MaterialTheme.customColors.consultationColor
                                    ),
                                    CategoryItem(
                                        id = "follow-up",
                                        emoji = "📋",
                                        title = "Follow-up",
                                        subtitle = "15-20 min",
                                        color = MaterialTheme.customColors.followUpColor
                                    ),
                                    CategoryItem(
                                        id = "routine",
                                        emoji = "🔄",
                                        title = "Routine",
                                        subtitle = "20-30 min",
                                        color = MaterialTheme.customColors.routineColor
                                    ),
                                    CategoryItem(
                                        id = "emergency",
                                        emoji = "🚨",
                                        title = "Emergency",
                                        subtitle = "Immediate",
                                        color = MaterialTheme.customColors.emergencyColor
                                    )
                                ),
                                selectedCategory = uiState.appointmentType,
                                onCategorySelected = { viewModel.updateAppointmentType(it) }
                            )
                            
                            Spacer(modifier = Modifier.height(28.dp))
                            
                            // Schedule Information
                            SectionTitle(title = "Appointment Details")
                            
                            Spacer(modifier = Modifier.height(16.dp))
                            
                            TaskCard(
                                title = "Schedule Information",
                                badge = if (uiState.appointmentType == "emergency") "Urgent" else "Today",
                                badgeColor = if (uiState.appointmentType == "emergency") {
                                    MaterialTheme.customColors.emergencyColor
                                } else {
                                    MaterialTheme.customColors.consultationColor
                                }
                            ) {
                                DateTimeRow(
                                    date = uiState.appointmentDate,
                                    time = uiState.appointmentTime,
                                    onDateClick = { showDatePicker = true },
                                    onTimeClick = { showTimePicker = true }
                                )
                                
                                Spacer(modifier = Modifier.height(12.dp))
                                
                                InputField(
                                    value = uiState.medicalRecordNumber,
                                    onValueChange = { viewModel.updateMedicalRecordNumber(it) },
                                    placeholder = "Medical Record Number (optional)",
                                    leadingIcon = "🏷️"
                                )
                            }
                            
                            Spacer(modifier = Modifier.height(16.dp))
                            
                            // Clinical Notes
                            TaskCard(
                                title = "Clinical Notes",
                                badge = "Optional",
                                badgeColor = MaterialTheme.customColors.followUpColor
                            ) {
                                InputField(
                                    value = uiState.clinicalNotes,
                                    onValueChange = { viewModel.updateClinicalNotes(it) },
                                    placeholder = "Initial observations or symptoms...",
                                    singleLine = false,
                                    modifier = Modifier.heightIn(min = 100.dp)
                                )
                            }
                            
                            Spacer(modifier = Modifier.height(20.dp))
                            
                            // Progress Bar
                            ProgressBar(progress = uiState.progress)
                            
                            Spacer(modifier = Modifier.height(20.dp))
                            
                            // Consent Card
                            ConsentCard(
                                isChecked = uiState.hasConsent,
                                onCheckedChange = { viewModel.updateConsent(it) },
                                text = "Patient consents to audio recording for medical documentation"
                            )
                            
                            Spacer(modifier = Modifier.height(24.dp))
                            
                            // Action Buttons
                            ActionButton(
                                text = "Let's Start Recording",
                                onClick = {
                                    if (viewModel.validateForm()) {
                                        toastState.showToast(
                                            message = "Appointment Created",
                                            subtitle = "Proceeding to recording...",
                                            type = ToastType.SUCCESS
                                        )
                                        onNavigateToRecording(
                                            AppointmentData(
                                                patientName = uiState.patientName,
                                                appointmentType = uiState.appointmentType ?: "",
                                                date = uiState.appointmentDate,
                                                time = uiState.appointmentTime,
                                                medicalRecordNumber = uiState.medicalRecordNumber,
                                                notes = uiState.clinicalNotes
                                            )
                                        )
                                    } else {
                                        toastState.showToast(
                                            message = "Please fill all required fields",
                                            type = ToastType.WARNING
                                        )
                                    }
                                },
                                enabled = uiState.isFormValid,
                                isLoading = uiState.isLoading
                            )
                            
                            Spacer(modifier = Modifier.height(16.dp))
                            
                            // Cancel Link
                            Box(
                                modifier = Modifier.fillMaxWidth(),
                                contentAlignment = Alignment.Center
                            ) {
                                TextLinkButton(
                                    text = "Cancel appointment",
                                    onClick = {
                                        if (uiState.hasUnsavedChanges) {
                                            // Show confirmation dialog
                                            onNavigateBack()
                                        } else {
                                            onNavigateBack()
                                        }
                                    }
                                )
                            }
                        }
                    }
                    
                    Spacer(modifier = Modifier.height(100.dp))
                }
                
                // Floating Action Button
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(30.dp)
                        .navigationBarsPadding()
                ) {
                    MedRecordFAB(
                        onClick = {
                            toastState.showToast(
                                message = "Quick Guide",
                                subtitle = "Fill patient info → Select type → Get consent → Start!",
                                type = ToastType.INFO,
                                duration = 5000L
                            )
                        },
                        icon = "?"
                    )
                }
                
                // Toast notifications
                toastState.currentToast?.let { toast ->
                    Box(
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .statusBarsPadding()
                    ) {
                        MedRecordToast(
                            message = toast.message,
                            subtitle = toast.subtitle,
                            type = toast.type,
                            duration = toast.duration,
                            onDismiss = { toastState.dismiss() }
                        )
                    }
                }
            }
        }
        
        // Date Picker Dialog
        if (showDatePicker) {
            DatePickerDialog(
                onDateSelected = { date ->
                    viewModel.updateDate(date)
                    showDatePicker = false
                },
                onDismissRequest = { showDatePicker = false }
            )
        }
        
        // Time Picker Dialog
        if (showTimePicker) {
            TimePickerDialog(
                onTimeSelected = { time ->
                    viewModel.updateTime(time)
                    showTimePicker = false
                },
                onDismissRequest = { showTimePicker = false }
            )
        }
    }
}

// Data class for passing appointment data
data class AppointmentData(
    val patientName: String,
    val appointmentType: String,
    val date: LocalDate,
    val time: LocalTime,
    val medicalRecordNumber: String,
    val notes: String
)

// Date Picker Dialog
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerDialog(
    onDateSelected: (LocalDate) -> Unit,
    onDismissRequest: () -> Unit
) {
    val datePickerState = rememberDatePickerState()
    
    DatePickerDialog(
        onDismissRequest = onDismissRequest,
        confirmButton = {
            TextButton(
                onClick = {
                    datePickerState.selectedDateMillis?.let { millis ->
                        // Convert millis to LocalDate
                        val date = java.time.Instant.ofEpochMilli(millis)
                            .atZone(java.time.ZoneId.systemDefault())
                            .toLocalDate()
                        onDateSelected(date)
                    }
                }
            ) {
                Text("OK")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismissRequest) {
                Text("Cancel")
            }
        }
    ) {
        DatePicker(state = datePickerState)
    }
}

// Time Picker Dialog
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimePickerDialog(
    onTimeSelected: (LocalTime) -> Unit,
    onDismissRequest: () -> Unit
) {
    val timePickerState = rememberTimePickerState()
    
    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = { Text("Select Time") },
        text = {
            TimePicker(state = timePickerState)
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onTimeSelected(
                        LocalTime.of(timePickerState.hour, timePickerState.minute)
                    )
                }
            ) {
                Text("OK")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismissRequest) {
                Text("Cancel")
            }
        }
    )
}