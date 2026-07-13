package com.unibo.mobile.data.models.dungeon

import com.unibo.mobile.domain.model.dungeon.ChallengeRating
import com.unibo.mobile.domain.model.dungeon.RoomTypeCombat

data class RoomTypeCombatImpl(
    override val roomTypeId: String,
    override val displayName: String,
    override val minEnemyCr: ChallengeRating,
    override val maxTotalCr: ChallengeRating,
    override val maxNumEnemies: Int
) : RoomTypeCombat