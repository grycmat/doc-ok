package com.gigapingu.docok.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.gigapingu.docok.data.AppointmentData
import com.gigapingu.docok.ui.components.*
import com.gigapingu.docok.ui.theme.DocOkTheme
import com.gigapingu.docok.ui.viewmodel.NewAppointmentViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewAppointmentScreen(
    onNavigateToRecording: (AppointmentData) -> Unit,
    onNavigateBack: () -> Unit,
    viewModel: NewAppointmentViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val scrollState = rememberScrollState()

    var showDatePicker by remember { mutableStateOf(false) }
    var showTimePicker by remember { mutableStateOf(false) }

    val context = LocalContext.current

    val appointmentTypes = listOf("Consultation", "Follow-up", "Routine", "Emergency")
    var isDropdownExpanded by remember { mutableStateOf(false) }

    DocOkTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
                    .padding(horizontal = 20.dp, vertical = 36.dp)
                    .navigationBarsPadding()
            ) {
                Spacer(modifier = Modifier.height(40.dp))

                // Header
                Column {
                    Text(
                        text = "Welcome, Dr. Smith",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = if (uiState.progress == 1f) {
                            "Ready to record!"
                        } else {
                            "${uiState.pendingAppointments} appointments pending"
                        },
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Spacer(modifier = Modifier.height(28.dp))

                // Patient Details Section
                SectionTitle(title = "Patient Details")
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    value = uiState.patientName,
                    onValueChange = { viewModel.updatePatientName(it) },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Patient Name") },
                    trailingIcon = {
                        IconButton(onClick = { /* TODO: Implement search functionality */ }) {
                            Icon(Icons.Default.Search, contentDescription = "Search Patient")
                        }
                    },
                    singleLine = true
                )
                Spacer(modifier = Modifier.height(12.dp))
                OutlinedTextField(
                    value = uiState.medicalRecordNumber,
                    onValueChange = { viewModel.updateMedicalRecordNumber(it) },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Medical Record Number (optional)") },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Filled.AccountBox,
                            contentDescription = null
                        )
                    },
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(28.dp))

                // Appointment Details Section
                SectionTitle(title = "Appointment Details")
                Spacer(modifier = Modifier.height(16.dp))

                ExposedDropdownMenuBox(
                    expanded = isDropdownExpanded,
                    onExpandedChange = { isDropdownExpanded = !isDropdownExpanded },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    OutlinedTextField(
                        value = uiState.appointmentType ?: "Select type",
                        onValueChange = {},
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor(),
                        readOnly = true,
                        label = { Text("Appointment Type") },
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = isDropdownExpanded)
                        }
                    )
                    ExposedDropdownMenu(
                        expanded = isDropdownExpanded,
                        onDismissRequest = { isDropdownExpanded = false }
                    ) {
                        appointmentTypes.forEach { type ->
                            DropdownMenuItem(
                                text = { Text(type) },
                                onClick = {
                                    viewModel.updateAppointmentType(type)
                                    isDropdownExpanded = false
                                }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                DateTimeRow(
                    date = uiState.appointmentDate,
                    time = uiState.appointmentTime,
                    onDateClick = { showDatePicker = true },
                    onTimeClick = { showTimePicker = true }
                )

                Spacer(modifier = Modifier.height(28.dp))

                // Clinical Notes Section
                SectionTitle(title = "Clinical Notes")
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    value = uiState.clinicalNotes,
                    onValueChange = { viewModel.updateClinicalNotes(it) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = 120.dp),
                    label = { Text("Initial observations or symptoms (optional)") },
                )

                Spacer(modifier = Modifier.height(28.dp))

                // Progress and Consent
                ProgressBar(progress = uiState.progress)
                Spacer(modifier = Modifier.height(20.dp))
                ConsentCard(
                    isChecked = uiState.hasConsent,
                    onCheckedChange = { viewModel.updateConsent(it) },
                    text = "Patient consents to audio recording for medical documentation"
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Actions
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

                Spacer(modifier = Modifier.height(12.dp))

                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    TextLinkButton(
                        text = "Cancel appointment",
                        onClick = { onNavigateBack() }
                    )
                }
                Spacer(modifier = Modifier.height(40.dp))
            }
        }

        if (showDatePicker) {
            DatePickerDialog(
                onDateSelected = { date ->
                    viewModel.updateDate(date)
                    showDatePicker = false
                },
                onDismissRequest = { showDatePicker = false }
            )
        }

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