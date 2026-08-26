package com.unibo.mobile.domain.models

/**
 * Represents an active game session save state.
 *
 * @property dungeon The dungeon data for this session.
 * @property characterPlayer The player's character data for this session.
 */
data class SaveSession(
    val dungeon: Dungeon,
    val characterPlayer: CharacterPlayer
)