package com.arturborowy.pins.model.system

import android.content.Context
import androidx.annotation.StringRes

class ResourcesRepository(private val context: Context) {

    fun getString(@StringRes stringResId: Int) = context.getString(stringResId)

    fun getString(@StringRes stringResId: Int, vararg args: Any) =
        context.getString(stringResId, *args)
}
