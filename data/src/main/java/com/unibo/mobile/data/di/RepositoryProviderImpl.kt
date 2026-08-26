package com.unibo.mobile.data.di

import com.unibo.mobile.data.local.dao.SaveGameDao
import com.unibo.mobile.data.remote.api.DndApi
import com.unibo.mobile.data.remote.api.SafeApiCaller
import com.unibo.mobile.data.remote.mappers.MonsterDtoListToListMapper
import com.unibo.mobile.data.remote.mappers.MonsterToEnemyMapper
import com.unibo.mobile.data.remote.mappers.SpellToAbilityMapper
import com.unibo.mobile.data.repositories.AbilityRepositoryImpl
import com.unibo.mobile.data.repositories.EnemyRepositoryImpl
import com.unibo.mobile.data.repositories.PlayerClassRepositoryImpl
import com.unibo.mobile.data.repositories.SaveGameRepositoryImpl
import com.unibo.mobile.domain.di.RepositoryProvider
import com.unibo.mobile.domain.repositories.AbilityRepository
import com.unibo.mobile.domain.repositories.EnemyRepository
import com.unibo.mobile.domain.repositories.PlayerClassRepository
import com.unibo.mobile.domain.repositories.SaveGameRepository

class RepositoryProviderImpl(
    saveGameDao: SaveGameDao,
    dndApi: DndApi,
) : RepositoryProvider {

    override val playerClassRepository: PlayerClassRepository = PlayerClassRepositoryImpl()

    override val abilityRepository: AbilityRepository = AbilityRepositoryImpl(
        dndApi = dndApi,
        safeApiCaller = SafeApiCaller(),
        spellToAbilityMapper = SpellToAbilityMapper()
    )

    override val enemyRepository: EnemyRepository = EnemyRepositoryImpl(
        dndApi = dndApi,
        safeApiCaller = SafeApiCaller(),
        monsterDtoListToListMapper = MonsterDtoListToListMapper(),
        monsterToEnemyMapper = MonsterToEnemyMapper(
            spellToAbilityMapper = SpellToAbilityMapper()
        )
    )

    override val saveGameRepository: SaveGameRepository = SaveGameRepositoryImpl(
        saveGameDao = saveGameDao,
        playerClassRepository = playerClassRepository,
        abilityRepository = abilityRepository
    )
}