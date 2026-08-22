package com.unibo.mobile.domain.models

/**
 * Represents a complete game save containing metadata and the active session.
 *
 * @property winCounter Total number of wins associated with this save.
 * @property saveSession The active game session data.
 */
data class SaveGame(
    val winCounter: Int,
    val saveSession: SaveSession?
)