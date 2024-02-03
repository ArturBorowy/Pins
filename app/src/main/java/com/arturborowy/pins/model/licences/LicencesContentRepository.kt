package com.arturborowy.pins.model.licences

import com.arturborowy.pins.R
import com.arturborowy.pins.model.system.ResourcesRepository

class LicencesContentRepository(private val resourcesRepository: ResourcesRepository) {

    val mit = resourcesRepository.getString(R.string.licence_mit_content)
    val apache_2_0 = resourcesRepository.getString(R.string.licence_apache_2_0_content)
}