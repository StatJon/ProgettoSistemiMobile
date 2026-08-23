package com.unibo.mobile.data.di

import com.unibo.mobile.data.local.dao.SaveGameDao
import com.unibo.mobile.data.remote.api.DndApi
import com.unibo.mobile.data.remote.api.SafeApiCaller
import com.unibo.mobile.data.remote.mappers.SpellToAbilityMapper
import com.unibo.mobile.data.repositories.AbilityRepositoryImpl
import com.unibo.mobile.data.repositories.PlayerClassRepositoryImpl
import com.unibo.mobile.data.repositories.SaveGameRepositoryImpl
import com.unibo.mobile.domain.di.RepositoryProvider
import com.unibo.mobile.domain.repositories.AbilityRepository
import com.unibo.mobile.domain.repositories.PlayerClassRepository
import com.unibo.mobile.domain.repositories.SaveGameRepository

class RepositoryProviderImpl(
    saveGameDao: SaveGameDao,
    playerClassRepository: PlayerClassRepository,
    dndApi: DndApi,
) : RepositoryProvider {

    override val playerClassRepository: PlayerClassRepository = PlayerClassRepositoryImpl()
    override val abilityRepository: AbilityRepository = AbilityRepositoryImpl(
        dndApi = dndApi,
        safeApiCaller = SafeApiCaller(),
        spellToAbilityMapper = TODO()
    )
    override val saveGameRepository: SaveGameRepository = SaveGameRepositoryImpl(
        saveGameDao = saveGameDao,
        playerClassRepository = playerClassRepository,
        abilityRepository = abilityRepository
    )
}