package com.unibo.mobile.domain.model.combat

interface CombatLogEntry {
    val actorName: String
    val actionName: String
    val targetName: String
    val wasHit: Boolean
    val attackRoll: Int?
    val defenseRoll: Int?
    val hpChanged: Int
    val mpChanged: Int?
}