package com.unibo.mobile.data.gamedata

import com.unibo.mobile.domain.models.AbilityDamage
import com.unibo.mobile.domain.models.ActionCost
import com.unibo.mobile.domain.models.DicesToRoll
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
            manaGrowth = 2,
            baseAbilityList = listOf(
                AbilityDamage(
                    name = "Mace",
                    level = 0,
                    isAoe = false,
                    actionCost = ActionCost.ACTION,
                    manaCost = 0,
                    dicesToRoll = DicesToRoll(1, 6),
                    requiresHitRoll = true
                )
            )
        ),
        PlayerClass(
            name = "Wyll",
            className = "warlock",
            unlockCounter = 2   ,
            baseHealthPoints = 10,
            baseManaPoints = 6,
            baseArmorClass = 14,
            baseAttackBonus = 2,
            healthGrowth = 6,
            manaGrowth = 4,
            baseAbilityList = listOf(
                AbilityDamage(
                    name = "Rapier",
                    level = 0,
                    isAoe = false,
                    actionCost = ActionCost.ACTION,
                    manaCost = 0,
                    dicesToRoll = DicesToRoll(1, 8),
                    requiresHitRoll = true
                )
            )
        )
    )
}