package com.arturborowy.brand.designsystem.size

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Immutable
data class Spacing(
    val buttonPadding: Dp = 4.dp,    // internal padding for button/chip text content
    val textSpacing: Dp = 8.dp,      // gap between adjacent inline text or icon elements
    val overlayPadding: Dp = 8.dp,   // outer inset of floating overlay cards on the map
    val cardPadding: Dp = 16.dp,     // inner content padding of cards and WideCard sections
    val cardMargin: Dp = 16.dp,      // outer margin separating cards from their container
    val screenPadding: Dp = 16.dp,   // outer inset for screen-level content areas
    val fabMargin: Dp = 16.dp,
    val fabPadding: Dp = 12.dp,
    val sectionSpacing: Dp = 24.dp,  // vertical gap after section/license headers

    val previewPadding: Dp = 16.dp,
)

internal val LocalSpacing = staticCompositionLocalOf { Spacing() }
