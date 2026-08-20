package com.unibo.mobile.data.di

import com.unibo.mobile.data.local.dao.SaveGameDao
import com.unibo.mobile.data.repositories.PlayerClassRepositoryImpl
import com.unibo.mobile.data.repositories.SaveGameRepositoryImpl
import com.unibo.mobile.domain.di.RepositoryProvider
import com.unibo.mobile.domain.repositories.PlayerClassRepository
import com.unibo.mobile.domain.repositories.SaveGameRepository

class RepositoryProviderImpl(
    saveGameDao: SaveGameDao,
    playerClassRepository: PlayerClassRepository
) : RepositoryProvider {

    override val playerClassRepository: PlayerClassRepository = PlayerClassRepositoryImpl()
    override val saveGameRepository: SaveGameRepository = SaveGameRepositoryImpl(
        saveGameDao = saveGameDao,
        playerClassRepository = playerClassRepository
    )
}