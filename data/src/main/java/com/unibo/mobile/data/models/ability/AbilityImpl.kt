package com.unibo.mobile.data.models.ability

import com.unibo.mobile.domain.model.ability.Ability
import com.unibo.mobile.domain.model.ability.AbilityType
import com.unibo.mobile.domain.model.ability.Dice

data class AbilityImpl(
    override val abilityId: String,
    override val displayName: String,
    override val actionCost: Int,
    override val level: Int,
    override val abilityType: AbilityType,
    override val diceCount: Int,
    override val diceType: Dice,
    override val flatExtraAmount: Int
) : Ability