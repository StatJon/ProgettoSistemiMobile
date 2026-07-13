package com.unibo.mobile.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "save_game")
data class SaveGameEntity(
    @PrimaryKey val id: Int = 0,
    val winCounter: Int,
    val hasActiveSession: Boolean,
    val currentRoomIndex: Int,
    val playerClassId: String,
    val playerCurrentHp: Int,
    val playerMaxHp: Int,
    val playerCurrentMp: Int,
    val playerMaxMp: Int,
    val playerExp: Int,
    val playerAbilityIds: String,
    val musicEnabled: Boolean,
    val musicVolume: Float,
    val soundEnabled: Boolean,
    val soundVolume: Float,
    val language: String
)
