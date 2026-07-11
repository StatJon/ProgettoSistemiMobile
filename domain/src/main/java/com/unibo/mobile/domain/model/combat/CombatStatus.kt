package com.unibo.mobile.domain.model.combat

import com.unibo.mobile.domain.model.entity.Ally
import com.unibo.mobile.domain.model.entity.CombatEntity
import com.unibo.mobile.domain.model.entity.Enemy

interface CombatStatus {
    val turnOrder: List<CombatEntity>
    val turnIndex: Int
    val log: List<CombatLogEntry>

    fun getActiveEnemy(): CombatEntity
    fun getAllEnemies(): List<Enemy>
    fun getAllAllies(): List<Ally>

}
