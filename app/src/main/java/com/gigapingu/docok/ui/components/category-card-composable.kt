package com.gigapingu.docok.ui.components
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.gigapingu.docok.ui.theme.*

/**
 * Category card for appointment types
 */
@Composable
fun CategoryCard(
    emoji: String,
    title: String,
    subtitle: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    categoryColor: Color = MaterialTheme.colorScheme.primary
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    
    // Animation values
    val scale by animateFloatAsState(
        targetValue = when {
            isPressed -> 0.95f
            isSelected -> 1.02f
            else -> 1f
        },
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "scale"
    )
    
    val elevation by animateDpAsState(
        targetValue = when {
            isPressed -> 2.dp
            isSelected -> 8.dp
            else -> 0.dp
        },
        animationSpec = tween(200),
        label = "elevation"
    )
    
    val borderColor by animateColorAsState(
        targetValue = if (isSelected) categoryColor else Color.Transparent,
        animationSpec = tween(200),
        label = "border color"
    )
    
    val backgroundColor by animateColorAsState(
        targetValue = if (isSelected) {
            MaterialTheme.colorScheme.surface
        } else {
            MaterialTheme.customColors.inputBackground
        },
        animationSpec = tween(200),
        label = "background color"
    )
    
    Card(
        modifier = modifier
            .scale(scale)
            .clip(RoundedCornerShape(16.dp))
            .clickable(
                interactionSource = interactionSource,
                indication = null
            ) { onClick() },
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = elevation),
        colors = CardDefaults.cardColors(containerColor = backgroundColor),
        border = BorderStroke(
            width = 2.dp,
            color = borderColor
        )
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            // Gradient overlay for selected state
            if (isSelected) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    categoryColor.copy(alpha = 0.05f),
                                    Color.Transparent
                                )
                            )
                        )
                )
            }
            
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                // Icon container
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(
                            if (isSelected) {
                                Brush.linearGradient(
                                    colors = listOf(
                                        categoryColor.copy(alpha = 0.9f),
                                        categoryColor
                                    )
                                )
                            } else {
                                Brush.linearGradient(
                                    colors = listOf(
                                        MaterialTheme.colorScheme.surface,
                                        MaterialTheme.colorScheme.surface
                                    )
                                )
                            }
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = emoji,
                        style = MaterialTheme.typography.headlineLarge,
                        textAlign = TextAlign.Center
                    )
                }
                
                Spacer(modifier = Modifier.height(12.dp))
                
                // Title
                Text(
                    text = title,
                    style = if (isSelected) {
                        CustomTextStyles.categoryTitle.copy(
                            color = categoryColor
                        )
                    } else {
                        CustomTextStyles.categoryTitle
                    },
                    textAlign = TextAlign.Center
                )
                
                Spacer(modifier = Modifier.height(4.dp))
                
                // Subtitle
                Text(
                    text = subtitle,
                    style = CustomTextStyles.categorySubtitle,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

/**
 * Grid of category cards
 */
@Composable
fun CategoryGrid(
    categories: List<CategoryItem>,
    selectedCategory: String?,
    onCategorySelected: (String) -> Unit,
    modifier: Modifier = Modifier,
    columns: Int = 2
) {
    val rows = (categories.size + columns - 1) / columns
    
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        for (row in 0 until rows) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                for (col in 0 until columns) {
                    val index = row * columns + col
                    if (index < categories.size) {
                        val category = categories[index]
                        CategoryCard(
                            emoji = category.emoji,
                            title = category.title,
                            subtitle = category.subtitle,
                            isSelected = selectedCategory == category.id,
                            onClick = { onCategorySelected(category.id) },
                            categoryColor = category.color,
                            modifier = Modifier.weight(1f)
                        )
                    } else {
                        // Empty space for incomplete rows
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
            }
        }
    }
}

data class CategoryItem(
    val id: String,
    val emoji: String,
    val title: String,
    val subtitle: String,
    val color: Color
)