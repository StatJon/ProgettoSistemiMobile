package com.unibo.mobile.data

import com.unibo.mobile.data.local.dao.SaveGameDao
import com.unibo.mobile.data.local.entities.SaveGameEntity
import com.unibo.mobile.data.models.save.SaveGameImpl
import com.unibo.mobile.data.models.save.UserSettingsImpl
import com.unibo.mobile.data.repositories.LocalRepositoryImpl
import kotlinx.coroutines.runBlocking
import org.junit.Test


class LocalRepositoryImplTest {
    class FakeSaveGameDao : SaveGameDao {
        private var stored: SaveGameEntity? = null
        override suspend fun getSaveGame(): SaveGameEntity? = stored
        override suspend fun insertOrUpdateSaveGame(entity: SaveGameEntity) {
            stored = entity
        }
    }

    @Test
    fun testSaveAndGetSaveGame(): Unit = runBlocking {
        val fakeDao = FakeSaveGameDao()
        val localRepository = LocalRepositoryImpl(fakeDao)
        val fakeSaveGame = SaveGameImpl(
            winCounter = 3,
            saveSession = null,
            userSettings = UserSettingsImpl(
                musicVolume = 0.7f, soundVolume = 0.5f,
                musicEnable = true, soundEnable = true, language = "it"
            )
        )
        localRepository.saveSaveGame(fakeSaveGame)
        val loaded = localRepository.getSaveGame()

        println(loaded)
        assert(loaded?.winCounter == 3)
    }
}
