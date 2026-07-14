package com.unibo.mobile.domain.model.entity

interface PlayerClass {
    val classId: String
    val displayName: String
    val unlockCountRequired: Int
    val strength: Int
    val dexterity: Int
    val constitution: Int
    val intelligence: Int
    val wisdom: Int
    val charisma: Int
}