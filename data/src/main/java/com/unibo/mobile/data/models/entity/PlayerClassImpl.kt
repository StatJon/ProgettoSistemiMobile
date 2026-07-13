package com.unibo.mobile.data.models.entity

import com.unibo.mobile.domain.model.entity.PlayerClass

data class PlayerClassImpl(
    override val classId: String,
    override val displayName: String,
    override val unlockCountRequired: Int
) : PlayerClass {
}