package com.arturborowy.pins.domain.licences

import com.arturborowy.pins.R
import com.arturborowy.pins.data.licences.LibrariesRepository
import com.arturborowy.pins.data.licences.LicencesContentRepository
import com.arturborowy.pins.data.system.ResourcesRepository

class LicencesInteractor(
    private val resourcesRepository: ResourcesRepository,
    private val librariesRepository: LibrariesRepository,
    private val licencesContentRepository: LicencesContentRepository
) {

    suspend fun getLicences() = listOf(
        Licence(
            resourcesRepository.getString(R.string.licence_apache_2_0_name),
            licencesContentRepository.apache_2_0,
            listOf(
                librariesRepository.mockk,
                librariesRepository.hilt,
                librariesRepository.leakCanary,
            )
        ),
        Licence(
            resourcesRepository.getString(R.string.licence_mit_name),
            licencesContentRepository.mit,
            listOf(librariesRepository.ultimateLoggerAndroid)
        )
    )
}