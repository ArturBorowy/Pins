package com.arturborowy.pins.domain.licences

data class Licence(
    val name: String,
    val content: String,
    val products: Collection<Product>
)

data class Product(
    val name: String,
    val year: String,
    val copyrightOwner: String
)