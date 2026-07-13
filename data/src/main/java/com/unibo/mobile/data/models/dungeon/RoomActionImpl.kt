package com.unibo.mobile.data.models.dungeon

import com.unibo.mobile.domain.model.dungeon.RoomAction

data class RoomActionImpl(
    override val actionId: String,
    override val displayName: String
) : RoomAction {
}