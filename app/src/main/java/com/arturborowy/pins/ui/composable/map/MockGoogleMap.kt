package com.arturborowy.pins.ui.composable.map

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arturborowy.brand.designsystem.BrandTheme
import com.arturborowy.pins.screen.map.MapMarkerItem

@Composable
fun MockGoogleMap(
    markerLists: List<List<MapMarkerItem>>,
    zoom: Float,
    cameraLatitude: Double,
    cameraLongitude: Double,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(8.dp))
            .background(BrandTheme.colorScheme.surfaceContainerHigh)
            .border(
                width = BrandTheme.sizing.strokeWidth,
                color = BrandTheme.colorScheme.outlineVariant,
                shape = RoundedCornerShape(8.dp)
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(BrandTheme.spacing.cardPadding)
        ) {
            Text(
                text = "Mock Map",
                style = BrandTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = BrandTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(BrandTheme.spacing.textSpacing))

            Text(
                text = "Lat: ${"%.4f".format(cameraLatitude)}, " +
                        "Lng: ${"%.4f".format(cameraLongitude)} (Zoom: ${zoom.toInt()})",
                style = BrandTheme.typography.bodySmall,
                color = BrandTheme.colorScheme.onSurfaceVariant,
                fontSize = 12.sp
            )

            val totalMarkers = markerLists.sumOf { it.size }
            if (totalMarkers > 0) {
                Spacer(modifier = Modifier.height(BrandTheme.spacing.textSpacing))
                Text(
                    text = "Markers: $totalMarkers",
                    style = BrandTheme.typography.labelSmall,
                    color = BrandTheme.colorScheme.primary
                )
            }
        }
    }
}
