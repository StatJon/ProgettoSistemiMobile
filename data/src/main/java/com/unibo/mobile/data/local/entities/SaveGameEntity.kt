package com.unibo.mobile.data.local.entities

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.unibo.mobile.domain.models.PlayerClass

@Entity(tableName = "save_game")
data class SaveGameEntity(
    @PrimaryKey val id: Int = 0,
    val winCounter: Int,
    val dungeonIndex: Int?,
    val dungeonSize: Int?,
    val playerClassName: String?,
    val currentManaPoints: Int?,
    val maxManaPoints: Int?,
    val name: String?,
    val maxHealthPoints: Int?,
    val currentHealthPoints: Int?,
    val armorClass: Int?,
    val abilityNames: String?,
    )