package com.unibo.mobile.domain.models


/**
 * Represents a player-controlled character in the game, extends base [Character].
 *
 * @property playerClass The class of the player character, defining base stats and abilities.
 * @property currentManaPoints The current mana points available to the player.
 * @property maxManaPoints The maximum mana points the player can have.
 * @property character The underlying [Character] data containing health and armor attributes.
 */
data class PlayerCharacter(
    val playerClass: PlayerClass,
    val currentManaPoints: Int,
    val maxManaPoints: Int,
    val character: Character
) {
    fun applyManaCost(costValue: Int): PlayerCharacter =
        copy(currentManaPoints = (currentManaPoints - costValue).coerceAtLeast(0))

    fun applyManaGain(gainValue: Int): PlayerCharacter =
        copy(currentManaPoints = (currentManaPoints + gainValue).coerceAtMost(maxManaPoints))

    companion object {
        fun createNewPlayer(playerClass: PlayerClass): PlayerCharacter = PlayerCharacter(
            playerClass = playerClass,
            currentManaPoints = playerClass.baseManaPoints,
            maxManaPoints = playerClass.baseManaPoints,
            character = Character(
                name = playerClass.name,
                maxHealthPoints = playerClass.baseHealthPoints,
                currentHealthPoints = playerClass.baseHealthPoints,
                armorClass = playerClass.baseArmorClass
            )
        )

        fun loadExistingPlayer(
            playerClass: PlayerClass,
            currentManaPoints: Int,
            currentHealthPoints: Int,
        ): PlayerCharacter = PlayerCharacter(
            playerClass = playerClass,
            currentManaPoints = currentManaPoints,
            maxManaPoints = playerClass.baseManaPoints,
            character = Character(
                name = playerClass.name,
                maxHealthPoints = playerClass.baseHealthPoints,
                currentHealthPoints = currentHealthPoints,
                armorClass = playerClass.baseArmorClass
            )
        )
    }
}