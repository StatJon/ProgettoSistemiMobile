package com.unibo.mobile.data.usecases

import com.unibo.mobile.domain.model.entity.PlayerClass
import com.unibo.mobile.domain.repositories.LocalRepository
import com.unibo.mobile.domain.usecases.StaticDataUseCase

class StaticDataUseCaseImpl(
    private val localRepository: LocalRepository
) : StaticDataUseCase {
    override fun getPlayerClasses(): List<PlayerClass> {
        return localRepository.getPlayerClasses()
    }
}