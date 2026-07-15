package com.unibo.mobile.domain.model.dungeon

interface Dungeon {
    val rooms: List<Room>
    val currentRoomIndex: Int
    fun advanceRoomIndex(): Dungeon
}