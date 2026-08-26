package com.unibo.mobile.repositories

import com.unibo.mobile.data.local.dao.SaveGameDao
import com.unibo.mobile.data.local.entities.SaveGameEntity
import com.unibo.mobile.data.repositories.SaveGameRepositoryImpl
import com.unibo.mobile.domain.models.PlayerClass
import com.unibo.mobile.domain.repositories.GamedataRepository
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test

/** Fake manuale del DAO: tiene i dati in memoria. */
class FakeSaveGameDao : SaveGameDao {
    private var storedEntity: SaveGameEntity? = null
    override suspend fun loadSaveGame(): SaveGameEntity? = storedEntity
    override suspend fun saveSaveGame(entity: SaveGameEntity) {
        storedEntity = entity
    }
}

/** Fake manuale del PlayerClassRepository: dati di test isolati. */
class FakeGamedataRepository : GamedataRepository {
    private val classes = listOf(
        PlayerClass(
            name = "Suor Mazzate",
            className = "cleric",
            unlockCounter = 0,
            baseHealthPoints = 12,
            baseManaPoints = 4,
            baseArmorClass = 14,
            baseAttackBonus = 2,
            healthGrowth = 8,
            manaGrowth = 2
        )
    )

    override suspend fun getPlayerClassByName(className: String): PlayerClass? =
        classes.find { it.className == className }

    override suspend fun getAllPlayerClasses(): List<PlayerClass> = classes
}

class SaveGameRepositoryImplTest {

    private lateinit var dao: FakeSaveGameDao
    private lateinit var playerClassRepository: FakeGamedataRepository
    private lateinit var saveRepository: SaveGameRepositoryImpl

    @Before
    fun setup() {
        dao = FakeSaveGameDao()
        playerClassRepository = FakeGamedataRepository()
        saveRepository = SaveGameRepositoryImpl(dao, playerClassRepository)
    }

    @Test
    fun loadEmpty() = runBlocking {
        val result = saveRepository.loadOrCreateGame()

        assertEquals(0, result.winCounter)
        assertNull(result.saveSession)
    }

    @Test
    fun loadWithSession() = runBlocking {
        dao.saveSaveGame(
            SaveGameEntity(
                winCounter = 3,
                dungeonIndex = 2,
                playerClassName = "cleric",
                currentManaPoints = 2,
                maxManaPoints = 4,
                name = "Suor Mazzate",
                maxHealthPoints = 12,
                currentHealthPoints = 8,
                armorClass = 14
            )
        )

        val result = saveRepository.loadOrCreateGame()

        assertEquals(3, result.winCounter)
        assertEquals(2, result.saveSession?.dungeonIndex)
        assertEquals(8, result.saveSession?.characterPlayer?.characterData?.currentHealthPoints)
    }

    @Test
    fun saveThenLoad() = runBlocking {
        val original = saveRepository.loadOrCreateGame()

        saveRepository.saveSaveGame(original.copy(winCounter = 5))
        val reloaded = saveRepository.loadOrCreateGame()

        assertEquals(5, reloaded.winCounter)
        assertNull(reloaded.saveSession)
    }
}