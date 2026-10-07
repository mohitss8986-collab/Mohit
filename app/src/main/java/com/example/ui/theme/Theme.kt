package com.example.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = SalonPlumDarkTheme,
    onPrimary = Color(0xFF480E3A),
    primaryContainer = SalonPlumPrimary,
    onPrimaryContainer = Color(0xFFFFD7F3),
    secondary = SalonGoldDarkTheme,
    onSecondary = Color(0xFF3F2E00),
    tertiary = SalonRoseDarkTheme,
    background = SalonBackgroundDark,
    surface = SalonSurfaceDark,
    surfaceVariant = SalonSurfaceVariantDark,
    onBackground = SalonTextPrimaryDark,
    onSurface = SalonTextPrimaryDark,
    onSurfaceVariant = SalonTextSecondaryDark
)

private val LightColorScheme = lightColorScheme(
    primary = SalonPlumPrimary,
    onPrimary = Color.White,
    primaryContainer = Color(0xFFFFD7F3),
    onPrimaryContainer = SalonPlumDark,
    secondary = SalonGoldSecondary,
    onSecondary = Color.White,
    secondaryContainer = Color(0xFFFFF0C2),
    onSecondaryContainer = Color(0xFF563D00),
    tertiary = SalonRoseTertiary,
    background = SalonBackground,
    surface = SalonSurface,
    surfaceVariant = SalonSurfaceVariant,
    onBackground = SalonTextPrimary,
    onSurface = SalonTextPrimary,
    onSurfaceVariant = SalonTextSecondary,
    outline = SalonOutline
)

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false, // Keep signature salon branding
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
