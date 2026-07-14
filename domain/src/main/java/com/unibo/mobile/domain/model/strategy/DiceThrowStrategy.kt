package com.unibo.mobile.domain.model.strategy

import com.unibo.mobile.domain.model.ability.Dice

interface DiceThrowStrategy {
    fun throwDice(
        diceQuantity: Int,
        diceType: Dice,
        modifier: Int
    ): Int
}