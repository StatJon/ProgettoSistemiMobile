package com.unibo.mobile.domain.usecases

import com.unibo.mobile.domain.model.dungeon.Dungeon
import com.unibo.mobile.domain.model.save.PlayerCharacter

interface DungeonUseCase {
    fun initDungeon(
        victoryCount: Int,
        savedDungeon: Dungeon?
    ) : Dungeon

    fun advanceToNextRoom(dungeon: Dungeon): Dungeon

    fun checkVictory(dungeon: Dungeon): Boolean

    fun checkDefeat(playerCharacter: PlayerCharacter): Boolean
}
