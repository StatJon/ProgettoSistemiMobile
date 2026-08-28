package com.unibo.mobile.data.remote.mappers

import com.unibo.mobile.data.remote.models.ActionDto
import com.unibo.mobile.data.remote.models.DamageDto
import com.unibo.mobile.data.remote.models.MonsterDto
import com.unibo.mobile.data.remote.models.SpellDto
import com.unibo.mobile.domain.models.Ability
import com.unibo.mobile.domain.models.ChallengeRating
import com.unibo.mobile.domain.models.CharacterData
import com.unibo.mobile.domain.models.CharacterEnemy
import com.unibo.mobile.domain.models.EnemyType

class MonsterToEnemyMapper(
    private val spellToAbilityMapper: SpellToAbilityMapper
) {
    fun invoke(monsterDto: MonsterDto, challengeRating: ChallengeRating): CharacterEnemy {
        return assembleCharacterEnemy(monsterDto, challengeRating)
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

    private fun assembleCharacterEnemy(
        monsterDto: MonsterDto,
        challengeRating: ChallengeRating
    ): CharacterEnemy {
        return CharacterEnemy(
            challengeRating = challengeRating,
            enemyType = mapMonsterTypeToEnemyType(monsterDto.type),
            characterData = CharacterData(
                name = monsterDto.name,
                maxHealthPoints = monsterDto.hitPoints,
                currentHealthPoints = monsterDto.hitPoints,
                armorClass = monsterDto.armorClass?.firstOrNull()?.value
                    ?: 10,
                abilityList = assembleAbilityList(monsterDto)
            ),

            )
    }

    private fun mapMonsterTypeToEnemyType(type: String): EnemyType {
        return when (type) {
            "aberration" -> EnemyType.ABERRATION
            "beast" -> EnemyType.BEAST
            "celestial" -> EnemyType.CELESTIAL
            "construct" -> EnemyType.CONSTRUCT
            "dragon" -> EnemyType.DRAGON
            "elemental" -> EnemyType.ELEMENTAL
            "fey" -> EnemyType.FEY
            "fiend" -> EnemyType.FIEND
            "giant" -> EnemyType.GIANT
            "humanoid" -> EnemyType.HUMANOID
            "monstrosity" -> EnemyType.MONSTROSITY
            "ooze" -> EnemyType.OOZE
            "plant" -> EnemyType.PLANT
            "undead" -> EnemyType.UNDEAD
            else -> EnemyType.MONSTROSITY
        }
    }


}