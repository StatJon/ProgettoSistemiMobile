package com.unibo.mobile.data.models.combat

import com.unibo.mobile.domain.model.combat.PostCombatReward

data class PostCombatRewardImpl(
    override val hpRestore: Int,
    override val mpRestore: Int
) :
    PostCombatReward {
}