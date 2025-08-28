package com.gigapingu.docok.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.gigapingu.docok.data.AppointmentData
import com.gigapingu.docok.ui.components.*
import com.gigapingu.docok.ui.theme.*
import com.gigapingu.docok.ui.viewmodel.NewAppointmentViewModel

@Composable
fun NewAppointmentScreen(
    onNavigateToRecording: (AppointmentData) -> Unit,
    onNavigateBack: () -> Unit,
    viewModel: NewAppointmentViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val scrollState = rememberScrollState()

    // Date and Time pickers
    var showDatePicker by remember { mutableStateOf(false) }
    var showTimePicker by remember { mutableStateOf(false) }

    val context = LocalContext.current

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
                                    TODO()
                                }
                            )
                            
                            Spacer(modifier = Modifier.height(24.dp))
                            
                            SectionTitle(
                                title = "Appointment Type",
                                showSeeAll = true,
                                onSeeAllClick = {
                                    TODO()
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
                            
                            ProgressBar(progress = uiState.progress)
                            
                            Spacer(modifier = Modifier.height(20.dp))
                            
                            ConsentCard(
                                isChecked = uiState.hasConsent,
                                onCheckedChange = { viewModel.updateConsent(it) },
                                text = "Patient consents to audio recording for medical documentation"
                            )
                            
                            Spacer(modifier = Modifier.height(24.dp))
                            
                            ActionButton(
                                text = "Start Recording",
                                onClick = {
                                    if (viewModel.validateForm()) {
                                        Toast.makeText(context, "Appointment Created", Toast.LENGTH_SHORT).show()
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
                                        Toast.makeText(context, "Please fill all required fields", Toast.LENGTH_SHORT).show()
                                    }
                                },
                                isLoading = uiState.isLoading
                            )
                            
                            Spacer(modifier = Modifier.height(16.dp))
                            
                            Box(
                                modifier = Modifier.fillMaxWidth(),
                                contentAlignment = Alignment.Center
                            ) {
                                TextLinkButton(
                                    text = "Cancel appointment",
                                    onClick = {
                                        onNavigateBack()
                                    }
                                )
                            }
                        }
                    }
                    
                    Spacer(modifier = Modifier.height(100.dp))
                }
                
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(30.dp)
                        .navigationBarsPadding()
                ) {

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


@Preview(showBackground = true)
@Composable
fun NewAppointmentScreenPreview() {
    DocOkTheme {
        NewAppointmentScreen(
            onNavigateToRecording = {},
            onNavigateBack = {}
        )
    }
}
