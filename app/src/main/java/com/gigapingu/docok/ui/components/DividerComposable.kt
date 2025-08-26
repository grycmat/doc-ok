package com.gigapingu.docok.ui.components
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.gigapingu.docok.ui.theme.customColors

/**
 * Custom divider with theme colors
 */
@Composable
fun MedRecordDivider(
    modifier: Modifier = Modifier,
    thickness: Dp = 1.dp,
    color: Color = MaterialTheme.customColors.divider,
    verticalPadding: Dp = 24.dp
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = verticalPadding)
            .height(thickness)
            .background(color)
    )
}

/**
 * Vertical divider for row layouts
 */
@Composable
fun VerticalDivider(
    modifier: Modifier = Modifier,
    thickness: Dp = 1.dp,
    color: Color = MaterialTheme.customColors.divider,
    horizontalPadding: Dp = 12.dp
) {
    Box(
        modifier = modifier
            .fillMaxHeight()
            .padding(horizontal = horizontalPadding)
            .width(thickness)
            .background(color)
    )
}