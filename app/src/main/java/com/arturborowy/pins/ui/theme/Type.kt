package com.arturborowy.pins.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

val Typography = Typography(
    displayLarge = TextStyle(
        fontSize = 57.sp,
        fontWeight = FontWeight.Normal,
        lineHeight = 64.sp,
        letterSpacing = (-0.25).sp,
    ),
    displayMedium = TextStyle(
        fontSize = 45.sp,
        fontWeight = FontWeight.Normal,
        lineHeight = 64.sp,
        letterSpacing = 0.sp,
    ),
    displaySmall = TextStyle(
        fontSize = 36.sp,
        fontWeight = FontWeight.Normal,
        lineHeight = 44.sp,
        letterSpacing = 0.sp,
    ),
    headlineLarge = TextStyle(
        fontSize = 32.sp,
        fontWeight = FontWeight.Normal,
        lineHeight = 40.sp,
        letterSpacing = 0.sp,
    ),
    headlineMedium = TextStyle(
        fontSize = 28.sp,
        fontWeight = FontWeight.Normal,
        lineHeight = 36.sp,
        letterSpacing = 0.sp,
    ),
    headlineSmall = TextStyle(
        fontSize = 24.sp,
        fontWeight = FontWeight.Normal,
        lineHeight = 32.sp,
        letterSpacing = 0.sp,
    ),
    titleLarge = TextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp,
    ),
    titleMedium = TextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 19.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.sp,
    ),
    titleSmall = TextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 17.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.sp,
    ),
    labelLarge = TextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 15.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.1.sp,
    ),
    labelMedium = TextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.25.sp,
    ),
    labelSmall = TextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.25.sp,
    ),
    bodyLarge = TextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 15.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.sp,
    ),
    bodyMedium = TextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 13.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.1.sp,
    ),
    bodySmall = TextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.25.sp,
    ),
)

val Typography.displayLargeEmphasized
    get() = displayLarge.copy(fontWeight = FontWeight.SemiBold)

val Typography.displayMediumEmphasized
    get() = displayMedium.copy(fontWeight = FontWeight.SemiBold)

val Typography.displaySmallEmphasized
    get() = displaySmall.copy(fontWeight = FontWeight.SemiBold)

val Typography.headlineLargeEmphasized
    get() = headlineLarge.copy(fontWeight = FontWeight.SemiBold)

val Typography.headlineMediumEmphasized
    get() = headlineMedium.copy(fontWeight = FontWeight.SemiBold)

val Typography.headlineSmallEmphasized
    get() = headlineSmall.copy(fontWeight = FontWeight.SemiBold)

val Typography.titleLargeEmphasized
    get() = titleLarge.copy(fontWeight = FontWeight.Bold)

val Typography.titleMediumEmphasized
    get() = titleMedium.copy(fontWeight = FontWeight.Bold)

val Typography.titleSmallEmphasized
    get() = titleSmall.copy(fontWeight = FontWeight.Bold)

val Typography.labelLargeEmphasized
    get() = labelLarge.copy(fontWeight = FontWeight.SemiBold)

val Typography.labelMediumEmphasized
    get() = labelMedium.copy(fontWeight = FontWeight.SemiBold)

val Typography.labelSmallEmphasized
    get() = labelSmall.copy(fontWeight = FontWeight.Bold)

val Typography.bodyLargeEmphasized
    get() = bodyLarge.copy(fontWeight = FontWeight.SemiBold)

val Typography.bodyMediumEmphasized
    get() = bodyMedium.copy(fontWeight = FontWeight.SemiBold)

val Typography.bodySmallEmphasized
    get() = bodySmall.copy(fontWeight = FontWeight.SemiBold)