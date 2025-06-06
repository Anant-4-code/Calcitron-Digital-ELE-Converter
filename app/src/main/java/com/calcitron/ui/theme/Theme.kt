package com.calcitron.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    primary = VibrantPurple,
    secondary = ElectricBlue,
    tertiary = NeonGreen,
    background = DarkBackground,
    surface = SurfaceDark,
    onPrimary = BrightText,
    onSecondary = BrightText,
    onTertiary = DarkBackground,
    onBackground = BrightText,
    onSurface = BrightText,
    primaryContainer = DeepPurple,
    secondaryContainer = ElectricBlue.copy(alpha = 0.15f),
    tertiaryContainer = NeonGreen.copy(alpha = 0.15f),
    error = NeonPink,
    errorContainer = NeonPink.copy(alpha = 0.15f),
    onError = BrightText,
    onErrorContainer = NeonPink
)

@Composable
fun CalcitronTheme(
    darkTheme: Boolean = true, // Always use dark theme for better neon effect
    content: @Composable () -> Unit
) {
    val colorScheme = DarkColorScheme
    val view = LocalView.current
    
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.background.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = false
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
} 