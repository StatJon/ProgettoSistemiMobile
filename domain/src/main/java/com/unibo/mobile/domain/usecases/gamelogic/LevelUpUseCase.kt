package com.unibo.mobile.domain.usecases.gamelogic

import com.unibo.mobile.domain.models.Ability
import com.unibo.mobile.domain.models.CharacterData
import com.unibo.mobile.domain.models.CharacterPlayer
import com.unibo.mobile.domain.repositories.AbilityRepository

interface LevelUpUseCase {
    suspend fun invoke(characterPlayer: CharacterPlayer): CharacterPlayer
}

class LevelUpUseCaseImpl(
    private val abilityRepository: AbilityRepository
) : LevelUpUseCase {

    val fallBackAbility: String = "fireball"

    override suspend fun invoke(characterPlayer: CharacterPlayer): CharacterPlayer {
        val newAbility: Ability? = assignNewAbility(characterPlayer)
        val newAbilityList = if (newAbility != null) {
            characterPlayer.characterData.abilityList + newAbility
        } else {
            characterPlayer.characterData.abilityList
        }

        return CharacterPlayer(
            playerClass = characterPlayer.playerClass,
            currentManaPoints = characterPlayer.currentManaPoints + characterPlayer.playerClass.manaGrowth,
            maxManaPoints = characterPlayer.maxManaPoints + characterPlayer.playerClass.manaGrowth,
            level = characterPlayer.level + 1,
            characterData = CharacterData(
                name = characterPlayer.characterData.name,
                maxHealthPoints = characterPlayer.characterData.maxHealthPoints + characterPlayer.playerClass.healthGrowth,
                currentHealthPoints = characterPlayer.characterData.currentHealthPoints + characterPlayer.playerClass.healthGrowth,
                armorClass = characterPlayer.playerClass.baseArmorClass,
                abilityList = newAbilityList
            )
        )
    }

    private suspend fun assignNewAbility(characterPlayer: CharacterPlayer): Ability? {
        val abilityList: List<Pair<String, Int>>? =
            abilityRepository.getAbilityListIndexAndLevelFromClass(characterPlayer.playerClass.className)
        val levelFilteredAbilityIndexList: List<String>? =
            abilityList?.filter { it.second == characterPlayer.level }
                ?.map { it.first }//filter and extract index
        val selectedAbilityIndex: String =
            levelFilteredAbilityIndexList?.randomOrNull() ?: fallBackAbility //null until here, then fallback
        val newAbility = abilityRepository.getAbilityByName(selectedAbilityIndex)
        return newAbility
    }
}
