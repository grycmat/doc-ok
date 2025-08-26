package com.gigapingu.docok.data

import java.time.LocalDate
import java.time.LocalTime

data class AppointmentData(
    val patientName: String,
    val appointmentType: String,
    val date: LocalDate,
    val time: LocalTime,
    val medicalRecordNumber: String,
    val notes: String
)