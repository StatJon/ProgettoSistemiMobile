package com.unibo.mobile.data.models

import com.unibo.mobile.domain.model.ability.Ability
import com.unibo.mobile.domain.model.entity.Enemy
import com.unibo.mobile.domain.model.entity.EnemyType

data class EnemyImpl(
    override val enemyType: EnemyType,
    override val challengeRating: Float,
    override val name: String,
    override val currentHp: Int,
    override val maxHp: Int,
    override val currentAp: Int,
    override val maxAp: Int,
    override val armorClass: Int,
    override val initiative: Int,
    override val abilities: List<Ability>
) : Enemy {
    override fun changeHp(amount: Int): EnemyImpl {
        return this.copy(currentHp = currentHp + amount)
    }

    override fun changeAp(amount: Int): EnemyImpl {
        return this.copy(currentAp = currentAp + amount)
    }

}