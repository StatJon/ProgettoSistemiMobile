package com.unibo.mobile.data.gamedata

import com.unibo.mobile.domain.models.PlayerClass

object PlayerGamedata {
    val PlayerClassList: List<PlayerClass> = listOf(
        PlayerClass(
            name = "Suor Mazzate",
            className = "cleric",
            baseHealthPoints = 12,
            baseManaPoints = 4,
            baseArmorClass = 14,
            baseAttackBonus = 2,
            healthGrowth = 8,
            manaGrowth = 2
        )
    )
}