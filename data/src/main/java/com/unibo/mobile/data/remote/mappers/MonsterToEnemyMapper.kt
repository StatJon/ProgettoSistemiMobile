package com.unibo.mobile.data.remote.mappers

import com.unibo.mobile.data.remote.models.ActionDto
import com.unibo.mobile.data.remote.models.DamageDto
import com.unibo.mobile.data.remote.models.MonsterDto
import com.unibo.mobile.data.remote.models.SpellDto
import com.unibo.mobile.domain.models.Ability
import com.unibo.mobile.domain.models.CharacterData
import com.unibo.mobile.domain.models.CharacterEnemy

class MonsterToEnemyMapper(
    private val spellToAbilityMapper: SpellToAbilityMapper
) {
    fun invoke(monsterDto: MonsterDto): CharacterEnemy {
        return assembleCharacterEnemy(monsterDto)
    }

    private fun assembleAbilityList(monsterDto: MonsterDto): List<Ability> {
        return monsterDto.actions
            ?.filter { it.damage?.isNotEmpty() == true }
            ?.mapNotNull { assembleActionAbility(it) }
            ?: emptyList()
    }

    private fun assembleActionAbility(actionDto: ActionDto): Ability? {
        val damageDice = actionDto.damage?.firstOrNull()?.damageDice ?: return null
        val spellDto = SpellDto(
            index = actionDto.name,
            name = actionDto.name,
            level = 1,
            castingTime = "1 action",
            damage = DamageDto(
                damageAtSlotLevel = mapOf("1" to damageDice)
            ),
            healAtSlotLevel = null,
            areaOfEffect = null
        )

        return spellToAbilityMapper.invoke(spellDto)
    }

    private fun assembleCharacterEnemy(monsterDto: MonsterDto): CharacterEnemy {
        return CharacterEnemy(
            characterData = CharacterData(
                name = monsterDto.name,
                maxHealthPoints = monsterDto.hitPoints,
                currentHealthPoints = monsterDto.hitPoints,
                armorClass = monsterDto.armorClass?.firstOrNull()?.value
                    ?: 10,
                abilityList = assembleAbilityList(monsterDto)
            )
        )
    }
}