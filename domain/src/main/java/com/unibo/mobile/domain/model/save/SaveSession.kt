package com.unibo.mobile.domain.model.save

interface SaveSession {
    val currentRoomIndex: Int
    val playerCharacter: PlayerCharacter
    fun updateRoomIndex(newRoomIndex: Int): SaveSession
    fun updatePlayerCharacter(updatedCharacter: PlayerCharacter): SaveSession
}