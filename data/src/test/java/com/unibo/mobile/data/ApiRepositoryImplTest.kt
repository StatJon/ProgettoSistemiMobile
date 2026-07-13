package com.unibo.mobile.data

import com.unibo.mobile.data.local.dao.SaveGameDao
import com.unibo.mobile.data.local.entities.SaveGameEntity
import com.unibo.mobile.data.models.dungeon.RoomTypeCombatImpl
import com.unibo.mobile.data.remote.api.RetrofitClient
import com.unibo.mobile.data.repositories.ApiRepositoryImpl
import com.unibo.mobile.data.repositories.LocalRepositoryImpl
import com.unibo.mobile.domain.model.dungeon.ChallengeRating
import kotlinx.coroutines.runBlocking
import org.junit.Test

class ApiRepositoryImplTest {

    class FakeSaveGameDao : SaveGameDao {
        override suspend fun getSaveGame(): SaveGameEntity? = null
        override suspend fun insertOrUpdate(entity: SaveGameEntity) {}
    }


    @Test
    fun testGetEnemies(): Unit = runBlocking {
        val dndApi = RetrofitClient().dndService
        val localRepository = LocalRepositoryImpl(saveGameDao = FakeSaveGameDao())
        val apiRepository = ApiRepositoryImpl(dndApi, localRepository)

        val fakeRoomTypeCombat = RoomTypeCombatImpl(
            roomTypeId = "test_room",
            displayName = "test_room_display",
            minEnemyCr = ChallengeRating.CR_0,
            maxTotalCr = ChallengeRating.CR_1,
            maxNumEnemies = 3
        )
        println(fakeRoomTypeCombat)
        val enemies = apiRepository.getEnemies(fakeRoomTypeCombat)
        println("Trovati ${enemies.size} nemici")
        enemies.forEach { println(it) }
    }

}