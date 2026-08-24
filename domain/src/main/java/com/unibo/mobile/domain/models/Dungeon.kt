package com.unibo.mobile.domain.models

/**
 * Contains an active game session dungeon data.
 * @property dungeonIndex Index of the current dungeon.
 * @property dungeonLength Max index of the current dungeon.
 */
data class Dungeon(
    val dungeonIndex: Int,
    val dungeonLength : Int,
)