package com.unibo.mobile.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "save_game")
data class SaveGameEntity(
    @PrimaryKey val id: Int = 0,
    val winCounter: Int,
    )