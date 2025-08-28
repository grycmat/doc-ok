package com.gigapingu.docok.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gigapingu.docok.data.recorder.AudioRecorder
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.File

class RecordViewModel(private val audioRecorder: AudioRecorder) : ViewModel() {

    private val _uiState = MutableStateFlow(RecordUiState())
    val uiState = _uiState.asStateFlow()

    private var timerJob: Job? = null

    fun startRecording(outputFile: File) {
        viewModelScope.launch {
            audioRecorder.start(outputFile)
            _uiState.value = RecordUiState(isRecording = true)
            startTimer()
        }
    }

    fun stopRecording() {
        viewModelScope.launch {
            audioRecorder.stop()
            stopTimer()
            _uiState.value = RecordUiState()
        }
    }

    fun pauseRecording() {
        viewModelScope.launch {
            audioRecorder.pause()
            _uiState.value = _uiState.value.copy(isPaused = true)
            stopTimer()
        }
    }

    fun resumeRecording() {
        viewModelScope.launch {
            audioRecorder.resume()
            _uiState.value = _uiState.value.copy(isPaused = false)
            startTimer()
        }
    }

    private fun startTimer() {
        timerJob = viewModelScope.launch {
            while (true) {
                delay(1000)
                _uiState.value = _uiState.value.copy(duration = _uiState.value.duration + 1000)
            }
        }
    }

    private fun stopTimer() {
        timerJob?.cancel()
        timerJob = null
    }
}