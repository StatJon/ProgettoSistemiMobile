package com.unibo.mobile.domain.repositories

import com.unibo.mobile.domain.model.ability.Ability
import com.unibo.mobile.domain.model.dungeon.RoomTypeCombat
import com.unibo.mobile.domain.model.entity.Enemy
import com.unibo.mobile.domain.model.entity.EnemyType
import com.unibo.mobile.domain.model.entity.PlayerClass

interface ApiRepository {
    suspend fun getEnemies(room: RoomTypeCombat): List<Enemy>
    suspend fun getAbilitiesEnemy(enemy: EnemyType): List<Ability>
    suspend fun getAbilitiesPlayerCharacter(playerClass: PlayerClass, level: Int): List<Ability>
}

