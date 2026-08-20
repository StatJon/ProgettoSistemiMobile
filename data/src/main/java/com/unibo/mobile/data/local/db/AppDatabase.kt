package com.unibo.mobile.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.unibo.mobile.data.local.dao.SaveGameDao
import com.unibo.mobile.data.local.entities.SaveGameEntity

@Database(entities = [SaveGameEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun saveGameDao(): SaveGameDao
}