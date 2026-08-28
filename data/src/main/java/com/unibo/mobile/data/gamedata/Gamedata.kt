package com.unibo.mobile.data.gamedata

import com.unibo.mobile.domain.models.Ability
import com.unibo.mobile.domain.models.AbilityDamage
import com.unibo.mobile.domain.models.ActionCost
import com.unibo.mobile.domain.models.DicesToRoll
import com.unibo.mobile.domain.models.PlayerClass

object Gamedata {

    val baseDungeonLength = 5
    // val checkpointStep = 3 TODO: Aggiungere dopo primo collaudo gioco, non MVP
    // val baseActionPointsPerTurn = 2 TODO: Se serve aggiungere

    val fallBackLevelUpAbility: Ability = AbilityDamage(
        name = "Fireball",
        index = "fireball",
        level = 0,
        isAoe = true,
        actionCost = ActionCost.ACTION,
        manaCost = 3,
        dicesToRoll = DicesToRoll(2, 6),
        requiresHitRoll = true
    )

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
                    index = "mace",
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
                    index = "rapier",
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