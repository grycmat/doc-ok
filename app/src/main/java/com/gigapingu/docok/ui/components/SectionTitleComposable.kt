package com.gigapingu.docok.ui.components
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.gigapingu.docok.ui.theme.*

/**
 * Section title with optional "See all" link
 */
@Composable
fun SectionTitle(
    title: String,
    modifier: Modifier = Modifier,
    showSeeAll: Boolean = false,
    onSeeAllClick: (() -> Unit)? = null
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            style = CustomTextStyles.sectionTitle,
            color = MaterialTheme.customColors.chipTextDefault
        )
        
        if (showSeeAll && onSeeAllClick != null) {
            TextButton(
                onClick = onSeeAllClick,
                contentPadding = PaddingValues(0.dp)
            ) {
                Text(
                    text = "See all",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}