package com.unibo.mobile.domain.models

/**
 * Represents a base game entity.
 *
 * @property name The name of the entity.
 * @property maxHealthPoints The maximum health points the entity can have.
 * @property currentHealthPoints The current health points of the entity.
 * @property armorClass The armor class value.
 */
data class CharacterData(
    val name: String,
    val maxHealthPoints: Int,
    val currentHealthPoints: Int,
    val armorClass: Int,
    val abilityList: List<Ability>
) {
    fun changeHealthPoints(amount: Int): CharacterData {
        return this.copy(
            currentHealthPoints = (currentHealthPoints + amount).coerceIn(
                0,
                maxHealthPoints
            )
        )
    }

    fun applyDamage(damageValue: Int): CharacterData {
        return this.copy(currentHealthPoints = (currentHealthPoints - damageValue).coerceAtLeast(0))
    }

    fun applyHeal(healValue: Int): CharacterData {
        return this.copy(
            currentHealthPoints = (currentHealthPoints + healValue).coerceAtMost(
                maxHealthPoints
            )
        )
    }
}
