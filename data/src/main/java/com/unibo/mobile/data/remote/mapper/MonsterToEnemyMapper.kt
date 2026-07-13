package com.unibo.mobile.data.remote.mapper

import com.unibo.mobile.data.models.AbilityImpl
import com.unibo.mobile.data.models.EnemyImpl
import com.unibo.mobile.data.remote.models.monster.ActionDto
import com.unibo.mobile.data.remote.models.monster.MonsterDto
import com.unibo.mobile.domain.model.ability.Ability
import com.unibo.mobile.domain.model.ability.Dice
import com.unibo.mobile.domain.model.entity.Enemy
import com.unibo.mobile.domain.model.entity.EnemyType

// Main Function
fun MonsterDto.toEnemyOrNull(enemyTypeList: List<EnemyType>): Enemy? {
    val actionList: List<Ability> = extractActions(this) ?: return null
    val enemyType: EnemyType = defineEnemyType(this, enemyTypeList) ?: return null
    val id: String = defineMonsterId(this)
    val displayName: String = defineDisplayName(this)
    val hp: Int = defineHp(this)
    val ap: Int = defineAp()
    val armorClass: Int = defineArmorClass(this)
    val initiative: Int = defineInitiative(this)
    val challengeRating: Float = defineChallengeRating(this)
    return assembleEnemy(
        id,
        displayName,
        hp,
        ap,
        armorClass,
        initiative,
        actionList,
        enemyType,
        challengeRating
    )
}

// Helper Functions
private fun extractActions(monsterDto: MonsterDto): List<Ability>? {
    val actionDtoList: List<ActionDto> = monsterDto.actions.filter { !it.damage.isNullOrEmpty() }
    if (actionDtoList.isEmpty()) return null
    val pairList: List<Pair<String, String>> = actionDtoList.map { actionDto ->
        val name = actionDto.name
        val damage = actionDto.damage!!.first().damageDice
        Pair(name, damage)
    }
    val abilityList: List<Ability> = pairList.map { pair ->
        val abilityId = pair.first
        val displayName = formatDisplayName(pair.first)
        val actionCost = DefaultValues.DEFAULT_ACTION_COST
        val level = DefaultValues.DEFAULT_MIN_LEVEL
        val abilityType = DefaultValues.DEFAULT_ABILITY_TYPE
        val damageRaw = pair.second.split("+")
        val diceRaw = damageRaw.first()
        val flatDamage = damageRaw.getOrNull(1)?.toInt() ?: 0
        val dicePair = diceRaw.split("d")
        val diceCount = dicePair.first().toInt()
        val diceFaces = dicePair[1].toInt()
        val diceType = Dice.entries.first { it.dice == diceFaces }

        AbilityImpl(
            abilityId = abilityId,
            displayName = displayName,
            actionCost = actionCost,
            level = level,
            abilityType = abilityType,
            diceCount = diceCount,
            diceType = diceType,
            flatExtraAmount = flatDamage
        )
    }
    return abilityList
}

private fun defineMonsterId(monsterDto: MonsterDto): String {
    return monsterDto.index
}

private fun defineDisplayName(monsterDto: MonsterDto): String {
    return monsterDto.name
}

private fun formatDisplayName(rawId: String): String {
    return rawId
        .split("-")
        .joinToString(" ") { word -> word.replaceFirstChar { it.uppercase() } }
} // Helper per extractActions() per Monster.Ability.displayName, non Monster.displayName

private fun defineHp(monsterDto: MonsterDto): Int {
    return monsterDto.hitPoints
}

private fun defineAp(): Int {
    return DefaultValues.DEFAULT_ACTION_COST
}

private fun defineArmorClass(monsterDto: MonsterDto): Int {
    return monsterDto.armorClassDto.first().value
}

private fun defineInitiative(monsterDto: MonsterDto): Int {
    return monsterDto.dexterity
}

private fun defineEnemyType(monsterDto: MonsterDto, enemyTypeList: List<EnemyType>): EnemyType? {
    val enemyTypeRaw = monsterDto.type
    val enemyType = enemyTypeList.firstOrNull() { it.enemyTypeId == enemyTypeRaw } ?: return null
    return enemyType
}

private fun defineChallengeRating(monsterDto: MonsterDto): Float {
    return monsterDto.challengeRating
}

private fun assembleEnemy(
    id: String,
    name: String,
    maxHp: Int,
    maxAp: Int,
    armorClass: Int,
    initiative: Int,
    abilities: List<Ability>,
    enemyType: EnemyType,
    challengeRating: Float
): Enemy {
    return EnemyImpl(
        entityId = id,
        displayName = name,
        currentHp = maxHp,
        maxHp = maxHp,
        currentAp = maxAp,
        maxAp = maxAp,
        armorClass = armorClass,
        initiative = initiative,
        abilities = abilities,
        enemyType = enemyType,
        challengeRating = challengeRating
    )
}


