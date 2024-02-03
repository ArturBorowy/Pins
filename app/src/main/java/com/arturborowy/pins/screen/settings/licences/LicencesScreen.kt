package com.arturborowy.pins.screen.settings.licences

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.arturborowy.pins.R
import com.arturborowy.pins.domain.licences.Product
import com.arturborowy.pins.ui.composable.WideCard
import com.arturborowy.pins.utils.collectAsMutableState
import com.arturborowy.pins.utils.observeLifecycleEvents
import com.arturborowy.pins.utils.pxToDp
import com.arturborowy.pins.utils.statusBarHeightPx

@Composable
fun LicencesScreen(viewModel: LicencesViewModel = hiltViewModel()) {
    viewModel.observeLifecycleEvents(LocalLifecycleOwner.current.lifecycle)

    val (state, setState) = viewModel.state.collectAsMutableState()

    val androidStatusBarHeight = pxToDp(LocalContext.current.statusBarHeightPx ?: 0)

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(R.color.primary))
            .wrapContentSize(Alignment.Center)
    ) {
        item {
            Text(
                modifier = Modifier
                    .padding(16.dp, androidStatusBarHeight + 16.dp, 16.dp, 0.dp),
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                color = Color.White,
                text = stringResource(R.string.licences_header)
            )
        }
        items(state.licences) {
            LicenceItem(it.name, it.products, it.content)
        }
    }
}

object LicenceViewTag {
    const val LICENCE_NAME = "LICENCE_NAME"
    const val LICENCE_PRODUCT_NAME = "LICENCE_PRODUCT_NAME"
    const val LICENCE_PRODUCT_COPYRIGHT = "LICENCE_PRODUCT_COPYRIGHT"
    const val LICENCE_CONTENT = "LICENCE_CONTENT"
}

@Composable
fun LicenceItem(licenceName: String, products: Collection<Product>, licenceContent: String) {
    WideCard(
        padding = PaddingValues(16.dp),
        margin = PaddingValues(16.dp)
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
            .padding(0.dp, 0.dp, 0.dp, 24.dp)
            .testTag(LicenceViewTag.LICENCE_NAME),
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp
    )
}

@Composable
fun LicenceProduct(product: Product) {
    Text(
        text = product.name,
        modifier = Modifier
            .padding(0.dp, 0.dp, 0.dp, 4.dp)
            .testTag(LicenceViewTag.LICENCE_PRODUCT_NAME),
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp
    )
    Text(
        text = stringResource(R.string.licence_copyright, product.year, product.name),
        modifier = Modifier
            .padding(0.dp, 0.dp, 0.dp, 8.dp)
            .testTag(LicenceViewTag.LICENCE_PRODUCT_COPYRIGHT),
        fontSize = 14.sp
    )
}

@Composable
fun LicenceContent(licenceContent: String) {
    Text(
        text = licenceContent,
        modifier = Modifier
            .padding(0.dp, 16.dp, 0.dp, 0.dp)
            .testTag(LicenceViewTag.LICENCE_CONTENT),
        fontSize = 13.sp,
        fontStyle = FontStyle.Italic
    )
}