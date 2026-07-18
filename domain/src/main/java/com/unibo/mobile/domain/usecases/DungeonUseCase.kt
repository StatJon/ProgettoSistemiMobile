package com.unibo.mobile.domain.usecases

import com.unibo.mobile.domain.model.dungeon.Dungeon
import com.unibo.mobile.domain.model.entity.PlayerClass
import com.unibo.mobile.domain.model.save.PlayerCharacter

interface DungeonUseCase {
    fun initDungeon(
        winCounter: Int,
        savedDungeon: Dungeon?
    ): Dungeon

    fun advanceToNextRoom(dungeon: Dungeon): Dungeon

    fun checkVictory(dungeon: Dungeon, currentRoomIndex: Int): Boolean

    fun checkDefeat(playerCharacter: PlayerCharacter): Boolean

    fun createNewPlayerCharacter(playerClass: PlayerClass): PlayerCharacter
}
