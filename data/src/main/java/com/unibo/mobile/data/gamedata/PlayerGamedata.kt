package com.unibo.mobile.data.gamedata

import com.unibo.mobile.domain.models.PlayerClass

object PlayerGamedata {
    val PlayerClassList: List<PlayerClass> = listOf(
        PlayerClass(
            name = "Suor Mazzate",
            className = "cleric",
            unlockCounter = 0,
            baseHealthPoints = 12,
            baseManaPoints = 4,
            baseArmorClass = 14,
            baseAttackBonus = 2,
            healthGrowth = 8,
            manaGrowth = 2
        ),
        PlayerClass(
            name = "William",
            className = "sorcerer",
            unlockCounter = 2   ,
            baseHealthPoints = 10,
            baseManaPoints = 6,
            baseArmorClass = 14,
            baseAttackBonus = 2,
            healthGrowth = 6,
            manaGrowth = 4
        )
    )
}