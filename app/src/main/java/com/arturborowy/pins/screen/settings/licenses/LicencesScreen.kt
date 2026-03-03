package com.arturborowy.pins.screen.settings.licenses

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.arturborowy.pins.R
import com.arturborowy.pins.domain.licences.Product
import com.arturborowy.pins.ui.composable.PageTitle
import com.arturborowy.pins.ui.composable.WideCard
import com.arturborowy.pins.ui.theme.PinsTheme
import com.arturborowy.pins.ui.theme.spacing
import com.arturborowy.pins.ui.theme.titleLargeEmphasized
import com.arturborowy.pins.ui.theme.titleSmallEmphasized
import com.arturborowy.pins.utils.observeLifecycleEvents

@Composable
fun LicensesScreen(viewModel: LicensesViewModel = hiltViewModel()) {
    viewModel.observeLifecycleEvents(LocalLifecycleOwner.current.lifecycle)

    val state by viewModel.state.collectAsState()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(PinsTheme.colorScheme.background)
            .wrapContentSize(Alignment.Center)
            .testTag(LicenceViewTag.LICENCES_LIST)
    ) {
        item {
            PageTitle(
                text = stringResource(R.string.Licenses_header),
                modifier = Modifier.padding(
                    PinsTheme.spacing.medium,
                    PinsTheme.spacing.medium,
                    PinsTheme.spacing.medium,
                    0.dp
                )
            )
        }
        items(state.Licenses) {
            LicenceItem(it.name, it.products, it.content)
        }
    }
}

object LicenceViewTag {
    const val LICENCES_LIST = "LICENCES_LIST"
    const val LICENCE_NAME = "LICENCE_NAME"
    const val LICENCE_PRODUCT_NAME = "LICENCE_PRODUCT_NAME"
    const val LICENCE_PRODUCT_COPYRIGHT = "LICENCE_PRODUCT_COPYRIGHT"
    const val LICENCE_CONTENT = "LICENCE_CONTENT"
}

@Composable
fun LicenceItem(licenceName: String, products: Collection<Product>, licenceContent: String) {
    WideCard(
        padding = PaddingValues(PinsTheme.spacing.medium),
        margin = PaddingValues(PinsTheme.spacing.medium)
    ) {
        LicenceHeader(licenceName = licenceName)
        products.forEach {
            LicenceProduct(it)
        }
        LicenceContent(licenceContent)
    }
}

@Composable
fun LicenceHeader(licenceName: String) {
    Text(
        text = licenceName,
        modifier = Modifier
            .padding(bottom = PinsTheme.spacing.large)
            .testTag(LicenceViewTag.LICENCE_NAME),
        style = PinsTheme.typography.titleLargeEmphasized
    )
}

@Composable
fun LicenceProduct(product: Product) {
    Text(
        text = product.name,
        modifier = Modifier
            .padding(bottom = PinsTheme.spacing.xSmall)
            .testTag(LicenceViewTag.LICENCE_PRODUCT_NAME),
        style = PinsTheme.typography.titleSmallEmphasized
    )
    Text(
        text = stringResource(R.string.licence_copyright, product.year, product.name),
        modifier = Modifier
            .padding(bottom = PinsTheme.spacing.small)
            .testTag(LicenceViewTag.LICENCE_PRODUCT_COPYRIGHT),
        style = PinsTheme.typography.labelMedium
    )
}

@Composable
fun LicenceContent(licenceContent: String) {
    Text(
        text = licenceContent,
        modifier = Modifier
            .padding(top = PinsTheme.spacing.medium)
            .testTag(LicenceViewTag.LICENCE_CONTENT),
        style = PinsTheme.typography.bodyMedium,
        fontStyle = FontStyle.Italic
    )
}