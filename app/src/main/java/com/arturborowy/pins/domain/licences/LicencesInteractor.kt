package com.arturborowy.pins.domain.licences

import com.arturborowy.pins.R
import com.arturborowy.pins.data.Licenses.LicensesContentRepository
import com.arturborowy.pins.data.licences.LibrariesRepository
import com.arturborowy.pins.data.system.ResourcesRepository

class LicensesInteractor(
    private val resourcesRepository: ResourcesRepository,
    private val librariesRepository: LibrariesRepository,
    private val licensesContentRepository: LicensesContentRepository
) {

    suspend fun getLicenses() = listOf(
        Licence(
            resourcesRepository.getString(R.string.licence_apache_2_0_name),
            licensesContentRepository.apache_2_0,
            listOf(
                librariesRepository.mockk,
                librariesRepository.hilt,
                librariesRepository.leakCanary,
            )
        ),
        Licence(
            resourcesRepository.getString(R.string.licence_mit_name),
            licensesContentRepository.mit,
            listOf(librariesRepository.ultimateLoggerAndroid)
        )
    )
}