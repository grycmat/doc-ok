package com.gigapingu.docok.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalTime

class NewAppointmentViewModel : ViewModel() {
    
    private val _uiState = MutableStateFlow(NewAppointmentUiState())
    val uiState: StateFlow<NewAppointmentUiState> = _uiState.asStateFlow()
    
    init {
        // Generate MRN when patient name is entered
        viewModelScope.launch {
            _uiState.collect { state ->
                if (state.patientName.isNotEmpty() && state.medicalRecordNumber.isEmpty()) {
                    generateMRN()
                }
            }
        }
    }
    
    fun updatePatientName(name: String) {
        _uiState.update { currentState ->
            currentState.copy(
                patientName = name,
                hasUnsavedChanges = true
            ).also { updateProgress(it) }
        }
    }
    
    fun updateAppointmentType(type: String) {
        _uiState.update { currentState ->
            currentState.copy(
                appointmentType = type,
                hasUnsavedChanges = true
            ).also { updateProgress(it) }
        }
    }
    
    fun updateDate(date: LocalDate) {
        _uiState.update { currentState ->
            currentState.copy(
                appointmentDate = date,
                hasUnsavedChanges = true
            ).also { updateProgress(it) }
        }
    }
    
    fun updateTime(time: LocalTime) {
        _uiState.update { currentState ->
            currentState.copy(
                appointmentTime = time,
                hasUnsavedChanges = true
            ).also { updateProgress(it) }
        }
    }
    
    fun updateMedicalRecordNumber(mrn: String) {
        _uiState.update { currentState ->
            currentState.copy(
                medicalRecordNumber = mrn,
                hasUnsavedChanges = true
            )
        }
    }
    
    fun updateClinicalNotes(notes: String) {
        _uiState.update { currentState ->
            currentState.copy(
                clinicalNotes = notes,
                hasUnsavedChanges = true
            )
        }
    }
    
    fun updateConsent(hasConsent: Boolean) {
        _uiState.update { currentState ->
            currentState.copy(
                hasConsent = hasConsent,
                hasUnsavedChanges = true
            ).also { updateProgress(it) }
        }
    }
    
    private fun updateProgress(state: NewAppointmentUiState): NewAppointmentUiState {
        var progress = 0f
        
        if (state.patientName.isNotEmpty()) progress += 0.25f
        if (state.appointmentType != null) progress += 0.25f
        if (state.appointmentDate != LocalDate.now() || state.appointmentTime != LocalTime.now()) progress += 0.25f
        if (state.hasConsent) progress += 0.25f
        
        val isValid = state.patientName.isNotEmpty() && 
                     state.appointmentType != null && 
                     state.hasConsent
        
        return state.copy(
            progress = progress,
            isFormValid = isValid
        )
    }
    
    private fun generateMRN() {
        val mrn = "MRN-${(100000..999999).random()}"
        _uiState.update { currentState ->
            currentState.copy(medicalRecordNumber = mrn)
        }
    }
    
    fun validateForm(): Boolean {
        val state = _uiState.value
        return state.patientName.isNotEmpty() && 
               state.appointmentType != null && 
               state.hasConsent
    }
    
    fun startRecording() {
        _uiState.update { it.copy(isLoading = true) }
        
        viewModelScope.launch {
            // Simulate saving appointment
            kotlinx.coroutines.delay(1500)
            
            _uiState.update { it.copy(isLoading = false) }
            // Navigate to recording screen will be handled by the UI
        }
    }
    
    fun resetForm() {
        _uiState.value = NewAppointmentUiState()
    }
}

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