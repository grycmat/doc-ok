package com.gigapingu.docok.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gigapingu.docok.ui.components.record.NotesSection
import com.gigapingu.docok.ui.components.record.PatientCard
import com.gigapingu.docok.ui.components.record.RecordingCard
import com.gigapingu.docok.ui.theme.DocOkTheme
import kotlinx.coroutines.delay

@Composable
fun RecordScreen(
    modifier: Modifier = Modifier,
    patientName: String,
    medicalRecordNumber: String,
    appointmentType: String
) {
    var isRecording by remember { mutableStateOf(true) }
    var isPaused by remember { mutableStateOf(false) }
    var timer by remember { mutableStateOf("00:00") }
    var notes by remember { mutableStateOf("") }

    LaunchedEffect(isRecording, isPaused) {
        var seconds = 0
        while (isRecording) {
            delay(1000)
            if (!isPaused) {
                seconds++
                val mins = (seconds / 60).toString().padStart(2, '0')
                val secs = (seconds % 60).toString().padStart(2, '0')
                timer = "$mins:$secs"
            }
        }
    }

    Scaffold(
        modifier = modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            PatientCard(
                patientName = patientName,
                medicalRecordNumber = medicalRecordNumber,
                appointmentType = appointmentType
            )
            Spacer(modifier = Modifier.height(20.dp))
            RecordingCard(
                isRecording = isRecording,
                isPaused = isPaused,
                timer = timer,
                onPauseClick = { isPaused = !isPaused },
                onStopClick = { isRecording = false },
                onAddMarkerClick = { /*TODO*/ }
            )
            Spacer(modifier = Modifier.height(20.dp))
            NotesSection(
                notes = notes,
                onNotesChange = { notes = it }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun RecordScreenPreview() {
    DocOkTheme {
        RecordScreen(
            patientName = "John Doe",
            medicalRecordNumber = "MRN-123456",
            appointmentType = "Consultation"
        )
    }
}