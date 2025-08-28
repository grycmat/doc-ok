package com.gigapingu.docok.ui.viewmodel

data class RecordUiState(
    val isRecording: Boolean = false,
    val isPaused: Boolean = false,
    val duration: Long = 0L
)
