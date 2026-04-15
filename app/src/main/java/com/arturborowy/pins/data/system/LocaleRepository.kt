package com.arturborowy.pins.data.system

import java.util.Locale

class LocaleRepository {

    val locale: Locale get() = Locale.getDefault()
}
