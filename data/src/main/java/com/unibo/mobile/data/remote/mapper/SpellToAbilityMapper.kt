package com.unibo.mobile.data.remote.mapper

import com.unibo.mobile.data.models.AbilityImpl
import com.unibo.mobile.data.remote.models.spell.SpellDto
import com.unibo.mobile.domain.model.ability.Ability
import com.unibo.mobile.domain.model.ability.AbilityType
import com.unibo.mobile.domain.model.ability.Dice

// Extension Function per SpellDto

// Main Function
fun SpellDto.toAbilityOrNull(): Ability? {
    // Null OR Compatible check
    if (!isCompatibleSpell(this)) return null
    // functions for final assembler
    val abilityId: String = defineAbilityId(this)
    val displayName: String = formatDisplayName(this)
    val actionCost: Int = defineActionCost(this)
    val level: Int = defineLevel(this)
    val abilityType: AbilityType = defineAbilityType(this)
    val dicePair = parseDiceString(this, abilityType, level)
    val diceCount: Int = defineDiceCount(dicePair)
    val diceType: Dice = defineDiceType(dicePair)
    // final assembler
    return assembleAbility(
        abilityId,
        displayName,
        actionCost,
        level,
        abilityType,
        diceCount,
        diceType
    )
}

// Helper Functions
private fun isCompatibleSpell(spellDto: SpellDto): Boolean {
    return !spellDto.healAtSlotLevel.isNullOrEmpty() || !spellDto.damage?.damageAtSlotLevel.isNullOrEmpty()
}

private fun defineAbilityId(spellDto: SpellDto): String {
    return spellDto.index
}

private fun formatDisplayName(spellDto: SpellDto): String {
    return spellDto.name
}

private fun defineActionCost(spellDto: SpellDto): Int {
    return when (spellDto.castingTime) {
        "1 action" -> DefaultValues.STANDARD_ACTION_COST
        "1 bonus action" -> DefaultValues.BONUS_ACTION_COST
        else -> DefaultValues.DEFAULT_ACTION_COST
    }
}

private fun defineLevel(spellDto: SpellDto): Int {
    return when {
        spellDto.level == 0 -> DefaultValues.DEFAULT_MIN_LEVEL
        else -> spellDto.level
    }
}

private fun defineAbilityType(spellDto: SpellDto): AbilityType {
    return when {
        spellDto.damage != null && spellDto.areaOfEffect != null -> AbilityType.DAMAGE_AOE
        spellDto.damage != null && spellDto.areaOfEffect == null -> AbilityType.DAMAGE_SINGLE
        else -> AbilityType.HEAL_SINGLE
    }
}
//Nota: l'API DnD non espone alcun campo utile per dedurre AbilityType.HEAL_AOE,
//default per design a AbilityType.HEAL_SINGLE

private fun parseDiceString(
    spellDto: SpellDto,
    abilityType: AbilityType,
    level: Int
): Pair<Int, Dice> {
    val stringToParse = when (abilityType) {
        AbilityType.HEAL_SINGLE -> spellDto.healAtSlotLevel?.get(level.toString())
        else -> spellDto.damage?.damageAtSlotLevel?.get(level.toString())
    } ?: error("Nessun valore di danno/cura trovato per questa spell")

    val extractedDice = stringToParse.split(" ").first()
    val dividedDice = extractedDice.split("d")
    val diceCount = dividedDice[0].toInt()
    val diceFaces = dividedDice[1].toInt()

    val diceType = Dice.entries.first { it.dice == diceFaces }

    return Pair(diceCount, diceType)
}

private fun defineDiceCount(dicePair: Pair<Int, Dice>): Int {
    return dicePair.first
}

private fun defineDiceType(dicePair: Pair<Int, Dice>): Dice {
    return dicePair.second
}

private fun assembleAbility(
    abilityId: String,
    displayName: String,
    actionCost: Int,
    level: Int,
    abilityType: AbilityType,
    diceCount: Int,
    diceType: Dice
): Ability {
    return AbilityImpl(
        abilityId = abilityId,
        displayName = displayName,
        actionCost = actionCost,
        level = level,
        abilityType = abilityType,
        diceCount = diceCount,
        diceType = diceType,
        flatExtraAmount = DefaultValues.DEFAULT_FLAT_EXTRA
    )

}
