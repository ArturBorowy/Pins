package com.arturborowy.pins.ui.composable

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import com.arturborowy.brand.designsystem.BrandTheme
import com.arturborowy.brand.designsystem.typography.titleLargeEmphasized
import com.arturborowy.brand.designsystem.typography.titleSmallEmphasized
import com.arturborowy.pins.R
import com.arturborowy.pins.domain.licences.Product

object LicenceViewTag {
    const val LICENCES_LIST = "LICENCES_LIST"
    const val LICENCE_NAME = "LICENCE_NAME"
    const val LICENCE_PRODUCT_NAME = "LICENCE_PRODUCT_NAME"
    const val LICENCE_PRODUCT_COPYRIGHT = "LICENCE_PRODUCT_COPYRIGHT"
    const val LICENCE_CONTENT = "LICENCE_CONTENT"
}

@Composable
fun LicenceItem(licenceName: String, products: Collection<Product>, licenceContent: String) {
    WideCard {
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
            .padding(bottom = BrandTheme.spacing.sectionSpacing)
            .testTag(LicenceViewTag.LICENCE_NAME),
        style = BrandTheme.typography.titleLargeEmphasized
    )
}

@Composable
fun LicenceProduct(product: Product) {
    Text(
        text = product.name,
        modifier = Modifier
            .padding(bottom = BrandTheme.spacing.buttonPadding)
            .testTag(LicenceViewTag.LICENCE_PRODUCT_NAME),
        style = BrandTheme.typography.titleSmallEmphasized
    )
    Text(
        text = stringResource(R.string.licence_copyright, product.year, product.name),
        modifier = Modifier
            .padding(bottom = BrandTheme.spacing.textSpacing)
            .testTag(LicenceViewTag.LICENCE_PRODUCT_COPYRIGHT),
        style = BrandTheme.typography.labelMedium
    )
}

@Composable
fun LicenceContent(licenceContent: String) {
    Text(
        text = licenceContent,
        modifier = Modifier
            .padding(top = BrandTheme.spacing.cardPadding)
            .testTag(LicenceViewTag.LICENCE_CONTENT),
        style = BrandTheme.typography.bodyMedium,
        fontStyle = FontStyle.Italic
    )
}

@Preview
@Composable
private fun LicenceItemPreview() = PreviewTheme {
    LicenceItem(
        licenceName = "MIT License",
        products = listOf(
            Product(name = "Retrofit", year = "2023", copyrightOwner = "Square, Inc.")
        ),
        licenceContent = "Permission is hereby granted, free of charge, to any person obtaining a copy of this software..."
    )
}
