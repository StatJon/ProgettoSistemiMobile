package com.unibo.mobile.domain.models

import kotlin.random.Random

class DiceRoller(dicesToRoll: DicesToRoll) {

    fun rollDices(dicesToRoll: DicesToRoll): Int {
        val diceFaces = dicesToRoll.diceType.diceFaces
        val times = dicesToRoll.diceNumber
        var total = 0
        for (i in 0 until times) {
            total += (1..diceFaces).random()
        }
        return total
    }

}
