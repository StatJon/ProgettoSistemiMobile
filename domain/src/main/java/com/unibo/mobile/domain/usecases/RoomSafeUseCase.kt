package com.unibo.mobile.domain.usecases

import com.unibo.mobile.domain.model.dungeon.RoomAction
import com.unibo.mobile.domain.model.save.SaveSession

interface RoomSafeUseCase {
    fun applyRoomAction(
        saveSession: SaveSession,
        roomAction: RoomAction
    ): SaveSession
}
