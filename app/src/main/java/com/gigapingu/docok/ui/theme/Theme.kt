package com.gigapingu.docok.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

// Light color scheme
private val LightColorScheme = lightColorScheme(
    primary = Coral,
    onPrimary = TextOnPrimary,
    primaryContainer = CoralContainer,
    onPrimaryContainer = OnCoralContainer,

    secondary = MintGreen,
    onSecondary = TextOnSecondary,
    secondaryContainer = MintGreenContainer,
    onSecondaryContainer = OnMintGreenContainer,

    tertiary = SkyBlue,
    onTertiary = TextOnPrimary,
    tertiaryContainer = CardGradientStart2,
    onTertiaryContainer = TextPrimary,

    error = Error,
    onError = OnError,
    errorContainer = ErrorContainer,
    onErrorContainer = OnErrorContainer,

    background = BackgroundPrimary,
    onBackground = TextPrimary,

    surface = Surface,
    onSurface = TextPrimary,
    surfaceVariant = SurfaceVariant,
    onSurfaceVariant = TextSecondary,

    outline = Border,
    outlineVariant = BorderLight,

    scrim = ScrimMedium,
    inverseSurface = TextPrimary,
    inverseOnSurface = Surface,
    inversePrimary = CoralLight,

    surfaceTint = SurfaceTint
)

// Dark color scheme (optional - for future implementation)
private val DarkColorScheme = darkColorScheme(
    primary = CoralLight,
    onPrimary = TextPrimary,
    primaryContainer = CoralDark,
    onPrimaryContainer = CoralContainer,

    secondary = MintGreenLight,
    onSecondary = TextPrimary,
    secondaryContainer = MintGreenDark,
    onSecondaryContainer = MintGreenContainer,

    tertiary = SkyBlueLight,
    onTertiary = TextPrimary,
    tertiaryContainer = SkyBlueDark,
    onTertiaryContainer = CardGradientStart2,

    error = Error,
    onError = OnError,
    errorContainer = ErrorContainer,
    onErrorContainer = CoralContainer,

    background = DarkBackground,
    onBackground = DarkTextPrimary,

    surface = DarkSurface,
    onSurface = DarkTextPrimary,
    surfaceVariant = DarkSurfaceVariant,
    onSurfaceVariant = DarkTextSecondary,

    outline = Border,
    outlineVariant = BorderLight,

    scrim = ScrimDark,
    inverseSurface = Surface,
    inverseOnSurface = TextPrimary,
    inversePrimary = Coral,

    surfaceTint = CoralLight
)

// Custom color scheme for additional colors not in Material3
data class CustomColors(
    val success: Color = Success,
    val onSuccess: Color = OnSuccess,
    val successContainer: Color = SuccessContainer,
    val onSuccessContainer: Color = OnSuccessContainer,

    val warning: Color = Warning,
    val onWarning: Color = OnWarning,
    val warningContainer: Color = WarningContainer,
    val onWarningContainer: Color = OnWarningContainer,

    val info: Color = Info,
    val onInfo: Color = OnInfo,
    val infoContainer: Color = InfoContainer,
    val onInfoContainer: Color = OnInfoContainer,

    val recordingRed: Color = RecordingRed,
    val recordingRedPulsing: Color = RecordingRedPulsing,

    val consentGreen: Color = ConsentGreen,
    val consentBackground: Color = ConsentBackground,

    val consultationColor: Color = ConsultationColor,
    val followUpColor: Color = FollowUpColor,
    val routineColor: Color = RoutineColor,
    val emergencyColor: Color = EmergencyColor,

    val progressBackground: Color = ProgressBackground,
    val progressFillStart: Color = ProgressFillStart,
    val progressFillEnd: Color = ProgressFillEnd,

    val fabBackground: Color = FabBackground,
    val fabIcon: Color = FabIcon,

    val inputBackground: Color = InputBackground,
    val inputBorder: Color = InputBorder,
    val inputBorderFocused: Color = InputBorderFocused,
    val inputPlaceholder: Color = InputPlaceholder,

    val chipBackgroundDefault: Color = ChipBackgroundDefault,
    val chipBackgroundSelected: Color = ChipBackgroundSelected,
    val chipTextDefault: Color = ChipTextDefault,
    val chipTextSelected: Color = ChipTextSelected,

    val divider: Color = Divider,
    val shadowColor: Color = ShadowColor,
    val shadowColorStrong: Color = ShadowColorStrong,

    val cardGradientStart1: Color = CardGradientStart1,
    val cardGradientEnd1: Color = CardGradientEnd1,
    val cardGradientStart2: Color = CardGradientStart2,
    val cardGradientEnd2: Color = CardGradientEnd2,

    val backgroundGradientStart: Color = BackgroundGradientStart,
    val backgroundGradientEnd: Color = BackgroundGradientEnd
)

// Local composition for custom colors
val LocalCustomColors = staticCompositionLocalOf { CustomColors() }

// Extension property to access custom colors easily
val MaterialTheme.customColors: CustomColors
    @Composable
    get() = LocalCustomColors.current

@Composable
fun DocOkTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = Color.Transparent.toArgb()
            window.navigationBarColor = Color.Transparent.toArgb()

            // Set system bars appearance based on theme
            WindowCompat.getInsetsController(window, view).apply {
                isAppearanceLightStatusBars = !darkTheme
                isAppearanceLightNavigationBars = !darkTheme
            }
        }
    }

    // Provide custom colors through CompositionLocal
    CompositionLocalProvider(
        LocalCustomColors provides if (darkTheme) {
            CustomColors(
                // Override some colors for dark theme if needed
                inputBackground = DarkSurfaceVariant,
                inputBorder = Color(0xFF3A3F5F),
                divider = Color(0xFF3A3F5F)
            )
        } else {
            CustomColors()
        }
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = MedRecordTypography,
            content = content
        )
    }
}

// Utility composables for common use cases

/**
 * Get the color for appointment type
 */
@Composable
fun getAppointmentTypeColor(type: String): Color {
    return when (type.lowercase()) {
        "consultation" -> MaterialTheme.customColors.consultationColor
        "follow-up", "followup" -> MaterialTheme.customColors.followUpColor
        "routine" -> MaterialTheme.customColors.routineColor
        "emergency" -> MaterialTheme.customColors.emergencyColor
        else -> MaterialTheme.colorScheme.primary
    }
}

/**
 * Get gradient colors for decorative elements
 */
@Composable
fun getBackgroundGradientColors(): List<Color> {
    return listOf(
        MaterialTheme.customColors.backgroundGradientStart,
        MaterialTheme.customColors.backgroundGradientEnd
    )
}

/**
 * Get card gradient colors
 */
@Composable
fun getCardGradientColors(variant: Int = 1): List<Color> {
    return when (variant) {
        1 -> listOf(
            MaterialTheme.customColors.cardGradientStart1,
            MaterialTheme.customColors.cardGradientEnd1
        )
        2 -> listOf(
            MaterialTheme.customColors.cardGradientStart2,
            MaterialTheme.customColors.cardGradientEnd2
        )
        else -> listOf(
            MaterialTheme.colorScheme.surface,
            MaterialTheme.colorScheme.surface
        )
    }
}

/**
 * Get progress bar gradient colors
 */
@Composable
fun getProgressGradientColors(): List<Color> {
    return listOf(
        MaterialTheme.customColors.progressFillStart,
        MaterialTheme.customColors.progressFillEnd
    )
}