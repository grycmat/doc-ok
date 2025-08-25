package com.gigapingu.docok.ui.components
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gigapingu.docok.ui.theme.*

/**
 * Floating action button with gradient background
 */
@Composable
fun MedRecordFAB(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    icon: String = "?",
    contentDescription: String = "Help"
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.9f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "scale"
    )
    
    val rotation by animateFloatAsState(
        targetValue = if (isPressed) 90f else 0f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "rotation"
    )
    
    Box(
        modifier = modifier
            .size(56.dp)
            .scale(scale)
            .rotate(rotation)
            .shadow(
                elevation = 8.dp,
                shape = CircleShape,
                spotColor = MaterialTheme.customColors.consentGreen.copy(alpha = 0.4f)
            )
            .clip(CircleShape)
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        MaterialTheme.customColors.fabBackground,
                        MaterialTheme.customColors.consentGreen
                    )
                )
            )
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClickLabel = contentDescription
            ) { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = icon,
            fontSize = 24.sp,
            color = Color.White,
            textAlign = TextAlign.Center
        )
    }
}

/**
 * Recording FAB with pulsing animation
 */
@Composable
fun RecordingFAB(
    isRecording: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition(label = "recording")
    
    val pulseScale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulse"
    )
    
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    
    Box(
        modifier = modifier
            .size(64.dp)
            .scale(if (isRecording) pulseScale else 1f)
            .shadow(
                elevation = if (isRecording) 12.dp else 8.dp,
                shape = CircleShape,
                spotColor = if (isRecording) {
                    MaterialTheme.customColors.recordingRed.copy(alpha = 0.5f)
                } else {
                    MaterialTheme.customColors.shadowColorStrong
                }
            )
            .clip(CircleShape)
            .background(
                if (isRecording) {
                    Brush.radialGradient(
                        colors = listOf(
                            MaterialTheme.customColors.recordingRed,
                            MaterialTheme.customColors.recordingRedPulsing
                        )
                    )
                } else {
                    Brush.radialGradient(
                        colors = listOf(
                            MaterialTheme.colorScheme.primary,
                            MaterialTheme.customColors.progressFillStart
                        )
                    )
                }
            )
            .clickable(
                interactionSource = interactionSource,
                indication = null
            ) { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = if (isRecording) "⏹" else "🎤",
            fontSize = 28.sp,
            color = Color.White,
            textAlign = TextAlign.Center
        )
    }
}