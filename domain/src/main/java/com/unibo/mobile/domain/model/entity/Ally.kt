package com.unibo.mobile.domain.model.entity

interface Ally : CombatEntity {

    val currentMp: Int
    val maxMp: Int
    val playerClass: PlayerClass
    val playerExp: Int

    fun getCurrentLevel(): Int
    fun changeMp(amount: Int): Ally
}
