package com.arturborowy.pins.model.Licenses

import com.arturborowy.pins.domain.Licenses.Product

object LibrariesRepository {

    val mockk = Product(
        "MockK",
        "2017",
        "github.com/mockk"
    )
    val hilt = Product(
        "Hilt",
        "2020",
        "The Android Open Source Project"
    )

    val leakCanary = Product(
        "LeakCanary",
        "2015",
        "Square, Inc."
    )

    val ultimateLoggerAndroid = Product(
        "ultimate-logger-android",
        "2019",
        "Artur Borowy"
    )
}