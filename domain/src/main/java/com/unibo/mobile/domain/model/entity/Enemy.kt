package com.unibo.mobile.domain.model.entity

import com.unibo.mobile.domain.model.ability.Ability

interface Enemy : CombatEntity {
    val enemyType: EnemyType
    val challengeRating: Float
    val rewardExp: Int
}
