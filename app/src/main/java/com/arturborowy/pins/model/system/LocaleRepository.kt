package com.arturborowy.pins.model.system

import java.util.Locale

class LocaleRepository {

    val locale: Locale get() = Locale.getDefault()
}