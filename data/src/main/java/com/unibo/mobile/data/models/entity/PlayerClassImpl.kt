package com.unibo.mobile.data.models.entity

import com.unibo.mobile.domain.model.entity.PlayerClass

data class PlayerClassImpl(
    override val classId: String,
    override val displayName: String,
    override val unlockCountRequired: Int,
    override val strength: Int,
    override val dexterity: Int,
    override val constitution: Int,
    override val intelligence: Int,
    override val wisdom: Int,
    override val charisma: Int
) : PlayerClass {
}