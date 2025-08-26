package com.gigapingu.docok.ui.viewmodel

import java.time.LocalDate
import java.time.LocalTime

data class NewAppointmentUiState(
    val patientName: String = "",
    val appointmentType: String? = null,
    val appointmentDate: LocalDate = LocalDate.now(),
    val appointmentTime: LocalTime = LocalTime.now(),
    val medicalRecordNumber: String = "",
    val clinicalNotes: String = "",
    val hasConsent: Boolean = false,
    val progress: Float = 0f,
    val isFormValid: Boolean = false,
    val isLoading: Boolean = false,
    val hasUnsavedChanges: Boolean = false,
    val pendingAppointments: Int = 3
)