package com.arturborowy.pins.domain.Licenses

import com.arturborowy.pins.R
import com.arturborowy.pins.model.Licenses.LibrariesRepository
import com.arturborowy.pins.model.Licenses.LicensesContentRepository
import com.arturborowy.pins.model.system.ResourcesRepository

class LicensesInteractor(
    private val resourcesRepository: ResourcesRepository,
    private val librariesRepository: LibrariesRepository,
    private val LicensesContentRepository: LicensesContentRepository
) {

    suspend fun getLicenses() = listOf(
        Licence(
            resourcesRepository.getString(R.string.licence_apache_2_0_name),
            LicensesContentRepository.apache_2_0,
            listOf(
                librariesRepository.mockk,
                librariesRepository.hilt,
                librariesRepository.leakCanary,
            )
        ),
        Licence(
            resourcesRepository.getString(R.string.licence_mit_name),
            LicensesContentRepository.mit,
            listOf(librariesRepository.ultimateLoggerAndroid)
        )
    )
}