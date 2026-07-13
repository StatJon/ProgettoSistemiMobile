package com.unibo.mobile.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.unibo.mobile.data.local.entities.SaveGameEntity

@Dao
interface SaveGameDao {
    @Query("SELECT * FROM save_game WHERE id = 0")
    suspend fun getSaveGame(): SaveGameEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(entity: SaveGameEntity)
}