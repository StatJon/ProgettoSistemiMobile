package com.unibo.mobile.data.remote.mappers

import com.unibo.mobile.data.remote.models.SpellDto
import com.unibo.mobile.domain.models.Ability
import com.unibo.mobile.domain.models.AbilityDamage
import com.unibo.mobile.domain.models.AbilityHeal
import com.unibo.mobile.domain.models.ActionCost
import com.unibo.mobile.domain.models.DicesToRoll

class SpellToAbilityMapper{
    fun invoke(spellDto: SpellDto): Ability? {
        if (!isCompatibleSpell(spellDto)) return null
        return assembleAbility(spellDto)
    }

    private fun isCompatibleSpell(spellDto: SpellDto): Boolean {
        return !spellDto.healAtSlotLevel.isNullOrEmpty() || !spellDto.damage?.damageAtSlotLevel.isNullOrEmpty()
    }

    private fun determineAoe(spellDto: SpellDto): Boolean {
        return spellDto.areaOfEffect != null
    }

    private fun determineActionCost(spellDto: SpellDto): ActionCost {
        return when (spellDto.castingTime) {
            "1 bonus action" -> ActionCost.BONUS_ACTION
            "1 action" -> ActionCost.ACTION
            else -> ActionCost.ACTION
        }
    }

    private fun parseDice(spellDto: SpellDto): DicesToRoll {

        val rawDice = if (spellDto.healAtSlotLevel != null) {
            spellDto.healAtSlotLevel.entries.first()
        } else {
            spellDto.damage!!.damageAtSlotLevel.entries.first()
        }
        val splitRawDice = rawDice.value.split("d", " ")
        val diceNumber = splitRawDice.getOrNull(0)?.toIntOrNull() ?: 1
        val diceFaces = splitRawDice.getOrNull(1)?.toIntOrNull() ?: 1
        return DicesToRoll(
            diceNumber = diceNumber,
            diceFaces = diceFaces
        )
    }

    private fun assembleAbility(spellDto: SpellDto): Ability {

        if (spellDto.healAtSlotLevel != null) {
            return AbilityHeal(
                name = spellDto.name,
                level = spellDto.level,
                isAoe = determineAoe(spellDto),
                actionCost = determineActionCost(spellDto),
                manaCost = spellDto.level,
                dicesToRoll = parseDice(spellDto)
            )
        } else {
            return AbilityDamage(
                name = spellDto.name,
                level = spellDto.level,
                isAoe = determineAoe(spellDto),
                actionCost = determineActionCost(spellDto),
                manaCost = spellDto.level,
                dicesToRoll = parseDice(spellDto)
            )
        }
    }
}