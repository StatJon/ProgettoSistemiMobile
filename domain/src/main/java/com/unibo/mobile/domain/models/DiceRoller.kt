package com.unibo.mobile.domain.models

class DiceRoller(){

    fun invoke(dicesToRoll: DicesToRoll): Int {
        val diceFaces = dicesToRoll.diceFaces
        val times = dicesToRoll.diceNumber
        var total = 0
        for (i in 0 until times) {
            total += (1..diceFaces).random()
        }
        return total
    }

}
