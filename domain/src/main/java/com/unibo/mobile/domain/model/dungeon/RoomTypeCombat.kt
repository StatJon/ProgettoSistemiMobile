package com.unibo.mobile.domain.model.dungeon

interface RoomTypeCombat : RoomType {
    override val isSafe: Boolean get() = false
    val minEnemyCr: ChallengeRating
    val maxTotalCr: ChallengeRating
    val maxNumEnemies: Int
}