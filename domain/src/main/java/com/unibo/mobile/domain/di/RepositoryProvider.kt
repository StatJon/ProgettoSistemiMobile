package com.unibo.mobile.domain.di

import com.unibo.mobile.domain.repositories.AbilityRepository
import com.unibo.mobile.domain.repositories.EnemyRepository
import com.unibo.mobile.domain.repositories.GamedataRepository
import com.unibo.mobile.domain.repositories.SaveGameRepository

interface RepositoryProvider {
    val gamedataRepository: GamedataRepository
    val abilityRepository: AbilityRepository
    val enemyRepository: EnemyRepository
    val saveGameRepository: SaveGameRepository

}