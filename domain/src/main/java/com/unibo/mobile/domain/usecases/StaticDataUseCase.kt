package com.unibo.mobile.domain.usecases

import com.unibo.mobile.domain.model.entity.PlayerClass
import com.unibo.mobile.domain.repositories.LocalRepository

interface StaticDataUseCase {
    fun getPlayerClasses(): List<PlayerClass>
}