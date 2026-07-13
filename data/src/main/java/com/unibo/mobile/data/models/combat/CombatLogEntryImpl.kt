package com.unibo.mobile.data.models.combat

import com.unibo.mobile.domain.model.combat.CombatLogEntry

data class CombatLogEntryImpl(
    override val actorName: String,
    override val actionName: String,
    override val targetName: String,
    override val wasHit: Boolean,
    override val attackRoll: Int?,
    override val defenseRoll: Int?,
    override val hpChanged: Int,
    override val mpChanged: Int?
) : CombatLogEntry {
}