package com.unibo.mobile.data.models.strategy

import com.unibo.mobile.domain.model.ability.Dice
import com.unibo.mobile.domain.model.strategy.DiceThrowStrategy

class DiceThrowStrategyDefault : DiceThrowStrategy{
    override fun throwDice(
        diceQuantity: Int,
        diceType: Dice,
        modifier: Int
    ): Int {
        var diceSum = 0
        repeat(diceQuantity) {
            diceSum += (1..diceType.dice).random()
        }
        return diceSum + modifier
    }
}