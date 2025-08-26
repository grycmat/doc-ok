package com.gigapingu.docok.ui.components

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.gigapingu.docok.ui.theme.*

/**
 * Consent card with animated checkbox
 */
@Composable
fun ConsentCard(
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    text: String = "Patient consents to audio recording for medical documentation",
    modifier: Modifier = Modifier
) {
    val scale by animateFloatAsState(
        targetValue = if (isChecked) 1.02f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "scale"
    )
    
    val backgroundColor by animateColorAsState(
        targetValue = if (isChecked) {
            MaterialTheme.customColors.consentBackground
        } else {
            MaterialTheme.customColors.cardGradientStart1
        },
        animationSpec = tween(300),
        label = "background color"
    )
    
    Card(
        modifier = modifier
            .fillMaxWidth()
            .scale(scale),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            backgroundColor,
                            MaterialTheme.customColors.cardGradientEnd1
                        )
                    )
                )
                .clickable { onCheckedChange(!isChecked) }
                .padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Icon
                AnimatedVisibility(
                    visible = true,
                    enter = scaleIn(animationSpec = tween(200)),
                    exit = scaleOut(animationSpec = tween(200))
                ) {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(
                                if (isChecked) {
                                    MaterialTheme.customColors.consentGreen
                                } else {
                                    MaterialTheme.customColors.consentGreen.copy(alpha = 0.3f)
                                }
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "🔒",
                            style = MaterialTheme.typography.bodyLarge,
                            color = Color.White
                        )
                    }
                }
                
                // Content
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = text,
                        style = CustomTextStyles.consentText,
                        color = MaterialTheme.customColors.chipTextDefault
                    )
                }
                
                // Custom checkbox
                CustomCheckbox(
                    checked = isChecked,
                    onCheckedChange = onCheckedChange
                )
            }
        }
    }
}

/**
 * Custom animated checkbox
 */
@Composable
fun CustomCheckbox(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    val checkboxScale by animateFloatAsState(
        targetValue = if (checked) 1f else 0.8f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "checkbox scale"
    )
    
    val borderColor by animateColorAsState(
        targetValue = if (checked) {
            MaterialTheme.colorScheme.primary
        } else {
            MaterialTheme.customColors.inputBorder
        },
        animationSpec = tween(200),
        label = "border color"
    )
    
    val backgroundColor by animateColorAsState(
        targetValue = if (checked) {
            MaterialTheme.colorScheme.primary
        } else {
            Color.Transparent
        },
        animationSpec = tween(200),
        label = "background color"
    )
    
    Box(
        modifier = modifier
            .size(24.dp)
            .scale(checkboxScale)
            .clip(RoundedCornerShape(4.dp))
            .border(
                width = 2.dp,
                color = borderColor,
                shape = RoundedCornerShape(4.dp)
            )
            .background(backgroundColor)
            .clickable { onCheckedChange(!checked) },
        contentAlignment = Alignment.Center
    ) {
        AnimatedVisibility(
            visible = checked,
            enter = scaleIn(animationSpec = tween(100)) + fadeIn(animationSpec = tween(100)),
            exit = scaleOut(animationSpec = tween(100)) + fadeOut(animationSpec = tween(100))
        ) {
            Text(
                text = "✓",
                style = MaterialTheme.typography.bodySmall,
                color = Color.White
            )
        }
    }
}