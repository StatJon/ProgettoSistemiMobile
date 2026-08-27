package com.unibo.mobile.domain.models

/**
 * Represents a player-controlled character in the game, extends base [CharacterData].
 *
 * @property playerClass The class of the player character, defining base stats and abilities.
 * @property currentManaPoints The current mana points available to the player.
 * @property maxManaPoints The maximum mana points the player can have.
 * @property characterData The underlying [CharacterData] data containing health and armor attributes.
 */
data class CharacterPlayer(
    val playerClass: PlayerClass,
    val currentManaPoints: Int,
    val maxManaPoints: Int,
    val level: Int,
    override val characterData: CharacterData
) : Character {
    fun applyManaCost(costValue: Int): CharacterPlayer =
        copy(currentManaPoints = (currentManaPoints - costValue).coerceAtLeast(0))

    fun applyManaGain(gainValue: Int): CharacterPlayer =
        copy(currentManaPoints = (currentManaPoints + gainValue).coerceAtMost(maxManaPoints))

    companion object {
        fun createNewPlayer(playerClass: PlayerClass): CharacterPlayer = CharacterPlayer(
            playerClass = playerClass,
            currentManaPoints = playerClass.baseManaPoints,
            maxManaPoints = playerClass.baseManaPoints,
            level = 1,
            characterData = CharacterData(
                name = playerClass.name,
                maxHealthPoints = playerClass.baseHealthPoints,
                currentHealthPoints = playerClass.baseHealthPoints,
                armorClass = playerClass.baseArmorClass,
                abilityList = playerClass.baseAbilityList
            )
        )

        fun loadExistingPlayer(
            playerClass: PlayerClass,
            currentManaPoints: Int,
            currentHealthPoints: Int,
            level: Int,
            abilityList: List<Ability>
        ): CharacterPlayer = CharacterPlayer(
            playerClass = playerClass,
            currentManaPoints = currentManaPoints,
            maxManaPoints = playerClass.baseManaPoints,
            level = level,
            characterData = CharacterData(
                name = playerClass.name,
                maxHealthPoints = playerClass.baseHealthPoints,
                currentHealthPoints = currentHealthPoints,
                armorClass = playerClass.baseArmorClass,
                abilityList = abilityList
            )
        )
    }
}