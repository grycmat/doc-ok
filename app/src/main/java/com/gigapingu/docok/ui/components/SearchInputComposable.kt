package com.gigapingu.docok.ui.components
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.gigapingu.docok.ui.theme.*

/**
 * Search-style input field with animated icon
 */
@Composable
fun SearchInput(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String = "Patient name...",
    enabled: Boolean = true,
    onSearchClick: (() -> Unit)? = null
) {
    var isFocused by remember { mutableStateOf(false) }
    val focusRequester = remember { FocusRequester() }
    val interactionSource = remember { MutableInteractionSource() }
    
    // Animate icon based on input state
    val hasValue = value.isNotEmpty()
    val iconScale by animateFloatAsState(
        targetValue = if (hasValue) 1.1f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "icon scale"
    )
    
    val iconBackgroundColor by animateColorAsState(
        targetValue = if (hasValue) {
            MaterialTheme.customColors.consentGreen
        } else {
            MaterialTheme.colorScheme.primary
        },
        animationSpec = tween(300),
        label = "icon color"
    )
    
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
    ) {
        // Background
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clip(CircleShape)
                .background(
                    if (isFocused) {
                        MaterialTheme.colorScheme.surface
                    } else {
                        MaterialTheme.customColors.inputBackground
                    }
                )
        )
        
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Text field
            Box(
                modifier = Modifier.weight(1f),
                contentAlignment = Alignment.CenterStart
            ) {
                BasicTextField(
                    value = value,
                    onValueChange = onValueChange,
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(focusRequester)
                        .onFocusChanged { isFocused = it.isFocused },
                    enabled = enabled,
                    textStyle = CustomTextStyles.inputText,
                    singleLine = true,
                    cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
                    decorationBox = { innerTextField ->
                        Box {
                            if (value.isEmpty()) {
                                Text(
                                    text = placeholder,
                                    style = CustomTextStyles.inputPlaceholder
                                )
                            }
                            innerTextField()
                        }
                    }
                )
            }
            
            Spacer(modifier = Modifier.width(12.dp))
            
            // Animated search/check icon
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .scale(iconScale)
                    .clip(CircleShape)
                    .background(iconBackgroundColor)
                    .clickable(
                        interactionSource = interactionSource,
                        indication = null
                    ) {
                        onSearchClick?.invoke()
                    },
                contentAlignment = Alignment.Center
            ) {
                AnimatedContent(
                    targetState = hasValue,
                    transitionSpec = {
                        (fadeIn(animationSpec = tween(200)) +
                                scaleIn(initialScale = 0.8f, animationSpec = tween(200)))
                            .togetherWith(
                                fadeOut(animationSpec = tween(100)) +
                                        scaleOut(targetScale = 0.8f, animationSpec = tween(100))
                            )
                    },
                    label = "icon animation"
                ) { hasText ->
                    Text(
                        text = if (hasText) "✓" else "🔍",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.White,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
        
        // Focus border animation
        if (isFocused) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(CircleShape)
                    .background(Color.Transparent)
            ) {
                // Optional: Add shadow or border effect when focused
            }
        }
    }
}