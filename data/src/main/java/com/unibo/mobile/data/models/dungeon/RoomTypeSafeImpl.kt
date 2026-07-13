package com.unibo.mobile.data.models.dungeon

import com.unibo.mobile.domain.model.dungeon.RoomAction
import com.unibo.mobile.domain.model.dungeon.RoomTypeSafe

data class RoomTypeSafeImpl(
    override val availableActions: List<RoomAction>,
    override val roomTypeId: String,
    override val displayName: String
) : RoomTypeSafe {
}