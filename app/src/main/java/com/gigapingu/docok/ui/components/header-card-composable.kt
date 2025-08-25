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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.gigapingu.docok.ui.theme.*

/**
 * Header card with user avatar and greeting message
 */
@Composable
fun HeaderCard(
    userName: String,
    subtitle: String,
    avatarEmoji: String = "👨‍⚕️",
    pendingCount: Int = 0,
    modifier: Modifier = Modifier
) {
    var visible by remember { mutableStateOf(false) }
    
    LaunchedEffect(Unit) {
        visible = true
    }
    
    AnimatedVisibility(
        visible = visible,
        enter = slideInVertically(
            initialOffsetY = { -40 },
            animationSpec = tween(500, easing = FastOutSlowInEasing)
        ) + fadeIn(animationSpec = tween(500))
    ) {
        Card(
            modifier = modifier
                .fillMaxWidth()
                .shadow(
                    elevation = 10.dp,
                    shape = RoundedCornerShape(20.dp),
                    spotColor = MaterialTheme.customColors.shadowColor
                ),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            )
        ) {
            Box(modifier = Modifier.fillMaxWidth()) {
                // Top gradient bar
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(4.dp)
                        .background(
                            brush = Brush.horizontalGradient(
                                colors = listOf(
                                    MaterialTheme.customColors.progressFillStart,
                                    MaterialTheme.customColors.followUpColor,
                                    MaterialTheme.customColors.consentGreen
                                )
                            )
                        )
                )
                
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Avatar
                    Box(
                        modifier = Modifier
                            .size(60.dp)
                            .clip(CircleShape)
                            .background(
                                brush = Brush.linearGradient(
                                    colors = listOf(
                                        MaterialTheme.colorScheme.primary,
                                        MaterialTheme.customColors.progressFillStart
                                    )
                                )
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = avatarEmoji,
                            style = MaterialTheme.typography.headlineMedium
                        )
                    }
                    
                    Spacer(modifier = Modifier.height(12.dp))
                    
                    // Greeting
                    Text(
                        text = "Hi $userName",
                        style = CustomTextStyles.greeting,
                        textAlign = TextAlign.Center
                    )
                    
                    Spacer(modifier = Modifier.height(4.dp))
                    
                    // Subtitle with animated color
                    val subtitleColor by animateColorAsState(
                        targetValue = if (pendingCount == 0) {
                            MaterialTheme.customColors.consentGreen
                        } else {
                            MaterialTheme.colorScheme.primary
                        },
                        animationSpec = tween(500),
                        label = "subtitle color"
                    )
                    
                    Text(
                        text = subtitle,
                        style = CustomTextStyles.subtitle.copy(color = subtitleColor),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}