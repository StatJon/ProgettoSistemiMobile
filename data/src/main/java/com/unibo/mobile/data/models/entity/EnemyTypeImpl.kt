package com.unibo.mobile.data.models.entity

import com.unibo.mobile.domain.model.entity.EnemyType

data class EnemyTypeImpl(
    override val enemyTypeId: String,
    override val displayName: String
) : EnemyType {
}