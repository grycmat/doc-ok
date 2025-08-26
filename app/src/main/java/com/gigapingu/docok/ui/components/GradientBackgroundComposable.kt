package com.gigapingu.docok.ui.components
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.gigapingu.docok.ui.theme.getBackgroundGradientColors
import androidx.compose.material3.MaterialTheme
import com.gigapingu.docok.ui.theme.customColors

/**
 * Animated gradient background with floating decorative circles
 */
@Composable
fun GradientBackground(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    val density = LocalDensity.current
    val gradientColors = getBackgroundGradientColors()
    
    // Animations for floating circles
    val infiniteTransition = rememberInfiniteTransition(label = "background")
    
    val circle1Y by infiniteTransition.animateFloat(
        initialValue = -100f,
        targetValue = -150f,
        animationSpec = infiniteRepeatable(
            animation = tween(15000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "circle1Y"
    )
    
    val circle2X by infiniteTransition.animateFloat(
        initialValue = -75f,
        targetValue = -125f,
        animationSpec = infiniteRepeatable(
            animation = tween(20000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "circle2X"
    )
    
    val circle3Scale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.2f,
        animationSpec = infiniteRepeatable(
            animation = tween(18000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "circle3Scale"
    )
    
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                brush = Brush.linearGradient(
                    colors = gradientColors,
                    start = Offset(0f, 0f),
                    end = Offset.Infinite
                )
            )
    ) {

        val themeColors = MaterialTheme.customColors
        // Decorative circles
        Canvas(modifier = Modifier.fillMaxSize()) {
            val canvasWidth = size.width
            val canvasHeight = size.height
            
            // Circle 1 - Top right, coral
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(
                        themeColors.progressFillStart.copy(alpha = 0.6f),
                        themeColors.progressFillStart.copy(alpha = 0.3f),
                        Color.Transparent
                    ),
                    center = Offset(canvasWidth - 100.dp.toPx(), circle1Y.dp.toPx()),
                    radius = 200.dp.toPx()
                ),
                radius = 200.dp.toPx(),
                center = Offset(canvasWidth - 100.dp.toPx(), circle1Y.dp.toPx())
            )
            
            // Circle 2 - Bottom left, mint green
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(
                        themeColors.consentGreen.copy(alpha = 0.6f),
                        themeColors.consentGreen.copy(alpha = 0.3f),
                        Color.Transparent
                    ),
                    center = Offset(circle2X.dp.toPx(), canvasHeight - 50.dp.toPx()),
                    radius = 150.dp.toPx()
                ),
                radius = 150.dp.toPx(),
                center = Offset(circle2X.dp.toPx(), canvasHeight - 50.dp.toPx())
            )
            
            // Circle 3 - Middle right, purple/blue
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(
                        themeColors.consultationColor.copy(alpha = 0.5f),
                        themeColors.consultationColor.copy(alpha = 0.2f),
                        Color.Transparent
                    ),
                    center = Offset(canvasWidth * 0.9f, canvasHeight * 0.4f),
                    radius = 100.dp.toPx() * circle3Scale
                ),
                radius = 100.dp.toPx() * circle3Scale,
                center = Offset(canvasWidth * 0.9f, canvasHeight * 0.4f)
            )
        }
        
        content()
    }
}