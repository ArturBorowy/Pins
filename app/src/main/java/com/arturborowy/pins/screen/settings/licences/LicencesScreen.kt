package com.arturborowy.pins.screen.settings.licences

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.arturborowy.brand.designsystem.BrandTheme
import com.arturborowy.pins.R
import com.arturborowy.pins.domain.licences.Licence
import com.arturborowy.pins.domain.licences.Product
import com.arturborowy.pins.ui.composable.LicenceItem
import com.arturborowy.pins.ui.composable.LicenceViewTag
import com.arturborowy.pins.ui.composable.PageTitle
import com.arturborowy.pins.ui.composable.PreviewLightAndDark
import com.arturborowy.pins.ui.composable.PreviewTheme
import com.arturborowy.pins.utils.ScreenshotTest
import com.arturborowy.pins.utils.observeLifecycleEvents

@Composable
fun LicencesScreen(viewModel: LicencesViewModel = hiltViewModel()) {
    viewModel.observeLifecycleEvents(LocalLifecycleOwner.current.lifecycle)

    val state by viewModel.state.collectAsStateWithLifecycle()

    LicencesView(
        licences = state.licences,
    )
}

@Composable
private fun LicencesView(
    licences: List<Licence>,
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(BrandTheme.colorScheme.background)
            .padding(horizontal = BrandTheme.spacing.cardPadding)
            .testTag(LicenceViewTag.LICENCES_LIST),
        verticalArrangement = Arrangement.spacedBy(BrandTheme.spacing.cardPadding)
    ) {
        item { Spacer(Modifier.height(BrandTheme.spacing.cardPadding)) }

        item {
            PageTitle(text = stringResource(R.string.licences_header))
        }
        items(licences) {
            LicenceItem(it.name, it.products, it.content)
        }

        item { Spacer(Modifier.height(BrandTheme.spacing.cardPadding)) }
    }
}

@PreviewLightAndDark
@ScreenshotTest
@Composable
private fun LicencesViewPreview() = PreviewTheme {
    LicencesView(
        licences = listOf(
            Licence(
                name = "Example Library",
                content = "Apache License 2.0",
                products = listOf(
                    Product(
                        name = "Example Library",
                        year = "2024",
                        copyrightOwner = "John Doe"
                    )
                )
            )
        )
    )
}
