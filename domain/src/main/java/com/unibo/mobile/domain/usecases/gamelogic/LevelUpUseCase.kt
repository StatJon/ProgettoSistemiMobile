package com.unibo.mobile.domain.usecases.gamelogic

import com.unibo.mobile.domain.models.Ability
import com.unibo.mobile.domain.models.AbilityDamage
import com.unibo.mobile.domain.models.AbilityHeal
import com.unibo.mobile.domain.models.CharacterData
import com.unibo.mobile.domain.models.CharacterPlayer
import com.unibo.mobile.domain.repositories.AbilityRepository
import com.unibo.mobile.domain.repositories.GamedataRepository

interface LevelUpUseCase {
    suspend fun invoke(characterPlayer: CharacterPlayer): CharacterPlayer
}

class LevelUpUseCaseImpl(
    private val abilityRepository: AbilityRepository,
    private val gamedataRepository: GamedataRepository
) : LevelUpUseCase {

    override suspend fun invoke(characterPlayer: CharacterPlayer): CharacterPlayer {
        val newLevel = characterPlayer.level + 1
        val newAbility = fetchNewAbility(
            className = characterPlayer.playerClass.className,
            level = newLevel
        )

        return CharacterPlayer(
            playerClass = characterPlayer.playerClass,
            currentManaPoints = characterPlayer.currentManaPoints + characterPlayer.playerClass.manaGrowth,
            maxManaPoints = characterPlayer.maxManaPoints + characterPlayer.playerClass.manaGrowth,
            level = newLevel,
            characterData = CharacterData(
                name = characterPlayer.characterData.name,
                maxHealthPoints = characterPlayer.characterData.maxHealthPoints + characterPlayer.playerClass.healthGrowth,
                currentHealthPoints = characterPlayer.characterData.currentHealthPoints + characterPlayer.playerClass.healthGrowth,
                armorClass = characterPlayer.playerClass.baseArmorClass,
                abilityList = characterPlayer.characterData.abilityList + newAbility
            )
        )
    }

    private suspend fun fetchNewAbility(className: String, level: Int): Ability {
        val candidateIndexes = abilityRepository
            .getAbilityListIndexAndLevelFromClass(className)
            ?.filter { it.second == level }
            ?.map { it.first }
            ?.toMutableList()
            ?: mutableListOf()

        while (candidateIndexes.isNotEmpty()) {
            val pickedIndex = candidateIndexes.random()
            candidateIndexes.remove(pickedIndex)

            val ability = abilityRepository.getAbilityByName(pickedIndex)
            if (ability != null && isValidAbility(ability)) {
                return ability
            }
            println("DEBUG: fetchNewAbility - invalid candidate: $pickedIndex")
        }

        println("DEBUG: fetchNewAbility - no valid candidates, using fallBack ability")
        return gamedataRepository.getFallBackLevelUpAbility()
    }

    private fun isValidAbility(ability: Ability): Boolean {
        val dice = when (ability) {
            is AbilityDamage -> ability.dicesToRoll
            is AbilityHeal -> ability.dicesToRoll
        }
        return dice.diceNumber > 0 && dice.diceFaces > 1
    }
}
