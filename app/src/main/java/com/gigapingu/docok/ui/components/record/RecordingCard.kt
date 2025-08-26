package com.gigapingu.docok.ui.components.record

import androidx.compose.animation.core.*//
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gigapingu.docok.R
import com.gigapingu.docok.ui.theme.DocOkTheme
import kotlinx.coroutines.delay

@Composable
fun RecordingCard(
    modifier: Modifier = Modifier,
    isRecording: Boolean,
    isPaused: Boolean,
    timer: String,
    onPauseClick: () -> Unit,
    onStopClick: () -> Unit,
    onAddMarkerClick: () -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        )
    ) {
        Column(
            modifier = Modifier
                .padding(30.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            RecordingStatus(isRecording = isRecording, isPaused = isPaused)
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = timer,
                style = MaterialTheme.typography.displayLarge,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(20.dp))
            Waveform(isPaused = isPaused)
            Spacer(modifier = Modifier.height(30.dp))
            Controls(
                isPaused = isPaused,
                onPauseClick = onPauseClick,
                onStopClick = onStopClick,
                onAddMarkerClick = onAddMarkerClick
            )
        }
    }
}

@Composable
private fun RecordingStatus(isRecording: Boolean, isPaused: Boolean) {
    val color = if (isPaused) Color.Gray else MaterialTheme.colorScheme.error
    val text = if (isPaused) "Paused" else "Recording"

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .background(color.copy(alpha = 0.1f))
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Box(
            modifier = Modifier
                .size(8.dp)
                .clip(CircleShape)
                .background(color)
        )
        Text(
            text = text,
            color = color,
            style = MaterialTheme.typography.labelMedium,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
private fun Waveform(isPaused: Boolean) {
    val infiniteTransition = rememberInfiniteTransition()
    val wave = (1..10).map {
        infiniteTransition.animateFloat(
            initialValue = 0.5f,
            targetValue = 1f,
            animationSpec = infiniteRepeatable(
                animation = tween(1000, easing = FastOutSlowInEasing),
                repeatMode = RepeatMode.Reverse
            )
        )
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(Color(0xFFFFF5F5)),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        for (i in 0..9) {
            Spacer(modifier = Modifier.width(3.dp))
            Box(
                modifier = Modifier
                    .width(4.dp)
                    .height((20 + (i % 5) * 15).dp * if (isPaused) 0.5f else wave[i].value)
                    .clip(RoundedCornerShape(2.dp))
                    .background(MaterialTheme.colorScheme.error)
            )
        }
    }
}

@Composable
private fun Controls(
    isPaused: Boolean,
    onPauseClick: () -> Unit,
    onStopClick: () -> Unit,
    onAddMarkerClick: () -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(20.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = onAddMarkerClick,
            modifier = Modifier
                .size(56.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.secondaryContainer)
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_marker),
                contentDescription = "Add Marker",
                tint = MaterialTheme.colorScheme.onSecondaryContainer
            )
        }
        IconButton(
            onClick = onPauseClick,
            modifier = Modifier
                .size(72.dp)
                .clip(CircleShape)
                .background(if (isPaused) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error)
        ) {
            Icon(
                imageVector = if (isPaused) Icons.Default.Mic else Icons.Default.Pause,
                contentDescription = if (isPaused) "Record" else "Pause",
                tint = Color.White
            )
        }
        IconButton(
            onClick = onStopClick,
            modifier = Modifier
                .size(56.dp)
                .clip(CircleShape)
                .background(Color(0xFF5CB85C))
        ) {
            Icon(
                imageVector = Icons.Default.Stop,
                contentDescription = "Stop",
                tint = Color.White
            )
        }
    }
}

@Preview
@Composable
private fun RecordingCardPreview() {
    var isPaused by remember { mutableStateOf(false) }
    var timer by remember { mutableStateOf("00:00") }

    LaunchedEffect(Unit) {
        var seconds = 0
        while (true) {
            delay(1000)
            if (!isPaused) {
                seconds++
                val mins = (seconds / 60).toString().padStart(2, '0')
                val secs = (seconds % 60).toString().padStart(2, '0')
                timer = "$mins:$secs"
            }
        }
    }

    DocOkTheme {
        RecordingCard(
            isRecording = true,
            isPaused = isPaused,
            timer = timer,
            onPauseClick = { isPaused = !isPaused },
            onStopClick = {},
            onAddMarkerClick = {}
        )
    }
}