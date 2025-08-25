package com.gigapingu.docok.ui.components
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.gigapingu.docok.ui.theme.*
import kotlinx.coroutines.delay

/**
 * Toast notification component
 */
@Composable
fun MedRecordToast(
    message: String,
    subtitle: String? = null,
    type: ToastType = ToastType.SUCCESS,
    duration: Long = 3000L,
    onDismiss: () -> Unit
) {
    var isVisible by remember { mutableStateOf(true) }
    
    LaunchedEffect(Unit) {
        delay(duration)
        isVisible = false
        delay(300) // Wait for exit animation
        onDismiss()
    }
    
    AnimatedVisibility(
        visible = isVisible,
        enter = slideInHorizontally(
            initialOffsetX = { it },
            animationSpec = tween(300, easing = FastOutSlowInEasing)
        ) + fadeIn(),
        exit = slideOutHorizontally(
            targetOffsetX = { it },
            animationSpec = tween(300, easing = FastOutSlowInEasing)
        ) + fadeOut()
    ) {
        Card(
            modifier = Modifier
                .padding(16.dp)
                .shadow(
                    elevation = 10.dp,
                    shape = RoundedCornerShape(10.dp),
                    spotColor = MaterialTheme.customColors.shadowColorStrong
                ),
            shape = RoundedCornerShape(10.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            )
        ) {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Icon
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .clip(CircleShape)
                        .background(
                            when (type) {
                                ToastType.SUCCESS -> MaterialTheme.customColors.success
                                ToastType.ERROR -> MaterialTheme.customColors.emergencyColor
                                ToastType.WARNING -> MaterialTheme.customColors.warning
                                ToastType.INFO -> MaterialTheme.customColors.info
                            }
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = when (type) {
                            ToastType.SUCCESS -> "✓"
                            ToastType.ERROR -> "✕"
                            ToastType.WARNING -> "!"
                            ToastType.INFO -> "i"
                        },
                        color = Color.White,
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
                
                // Text content
                Column {
                    Text(
                        text = message,
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.customColors.chipTextDefault
                    )
                    subtitle?.let {
                        Text(
                            text = it,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.customColors.chipTextDefault.copy(alpha = 0.7f)
                        )
                    }
                }
            }
        }
    }
}

enum class ToastType {
    SUCCESS, ERROR, WARNING, INFO
}

/**
 * Toast state holder for showing toasts
 */
@Composable
fun rememberToastState(): ToastState {
    return remember { ToastState() }
}

class ToastState {
    var currentToast by mutableStateOf<ToastData?>(null)
        private set
    
    fun showToast(
        message: String,
        subtitle: String? = null,
        type: ToastType = ToastType.SUCCESS,
        duration: Long = 3000L
    ) {
        currentToast = ToastData(message, subtitle, type, duration)
    }
    
    fun dismiss() {
        currentToast = null
    }
}

data class ToastData(
    val message: String,
    val subtitle: String?,
    val type: ToastType,
    val duration: Long
)