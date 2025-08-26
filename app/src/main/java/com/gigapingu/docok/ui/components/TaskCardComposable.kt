package com.gigapingu.docok.ui.components
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.gigapingu.docok.ui.theme.*

/**
 * Task-style card for form sections
 */
@Composable
fun TaskCard(
    title: String,
    badge: String? = null,
    badgeColor: Color = MaterialTheme.customColors.consultationColor,
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.customColors.inputBackground
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = title,
                    style = CustomTextStyles.sectionTitle.copy(
                        fontSize = MaterialTheme.typography.titleMedium.fontSize
                    )
                )
                
                badge?.let {
                    Badge(
                        text = it,
                        color = badgeColor
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            
            // Content
            content()
        }
    }
}

/**
 * Badge component for task cards
 */
@Composable
fun Badge(
    text: String,
    color: Color = MaterialTheme.colorScheme.primary,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(20.dp))
            .background(color)
            .padding(horizontal = 12.dp, vertical = 4.dp)
    ) {
        Text(
            text = text,
            style = CustomTextStyles.badgeText,
            color = Color.White
        )
    }
}