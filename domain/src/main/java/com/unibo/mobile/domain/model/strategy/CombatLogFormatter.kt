package com.unibo.mobile.domain.model.strategy

import com.unibo.mobile.domain.model.combat.CombatLogEntry

interface CombatLogFormatter {
    fun format(entry: CombatLogEntry): String
}