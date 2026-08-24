package com.unibo.mobile.domain.models

/**
 * Represents an active game session save state.
 *
 * @property dungeonIndex Index of the current dungeon.
 * @property dungeonSize Max index of the current dungeon.
 * @property playerCharacter The player's character data for this session.
 */
data class SaveSession(
    val dungeonIndex: Int,
    val dungeonSize : Int,
    val playerCharacter: CharacterPlayer
)