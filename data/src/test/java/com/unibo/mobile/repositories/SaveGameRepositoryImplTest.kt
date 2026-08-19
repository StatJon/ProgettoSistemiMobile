package com.unibo.mobile.repositories

import com.unibo.mobile.data.local.dao.SaveGameDao
import com.unibo.mobile.data.local.entities.SaveGameEntity
import com.unibo.mobile.data.repositories.SaveGameRepositoryImpl
import com.unibo.mobile.domain.models.PlayerClass
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test

/**
 * Fake manuale del DAO: implementa l'interfaccia reale, tiene i dati in memoria.
 */
class FakeSaveGameDao : SaveGameDao {
    private var storedEntity: SaveGameEntity? = null

    override suspend fun loadSaveGame(): SaveGameEntity? = storedEntity

    override suspend fun saveSaveGame(entity: SaveGameEntity) {
        storedEntity = entity
    }
}

class SaveGameRepositoryImplTest {

    private val cleric = PlayerClass(
        name = "Suor Mazzate",
        className = "cleric",
        baseHealthPoints = 12,
        baseManaPoints = 4,
        baseArmorClass = 14,
        baseAttackBonus = 2,
        healthGrowth = 8,
        manaGrowth = 2
    )

    private lateinit var dao: FakeSaveGameDao
    private lateinit var repository: SaveGameRepositoryImpl

    @Before
    fun setup() {
        dao = FakeSaveGameDao()
        repository = SaveGameRepositoryImpl(dao)
    }

    @Test
    fun loadEmpty() = runBlocking {
        val result = repository.loadOrCreateGame()

        assertEquals(0, result.winCounter)
        assertNull(result.saveSession)
    }

    @Test
    fun loadWithSession() = runBlocking {
        dao.saveSaveGame(
            SaveGameEntity(
                winCounter = 3,
                dungeonIndex = 2,
                playerClass = cleric,
                currentManaPoints = 2,
                maxManaPoints = 4,
                name = cleric.name,
                maxHealthPoints = 12,
                currentHealthPoints = 8,
                armorClass = 14
            )
        )

        val result = repository.loadOrCreateGame()

        assertEquals(3, result.winCounter)
        assertEquals(2, result.saveSession?.dungeonIndex)
        assertEquals(8, result.saveSession?.playerCharacter?.character?.currentHealthPoints)
    }

    @Test
    fun saveThenLoad() = runBlocking {
        val original = repository.loadOrCreateGame()

        repository.saveSaveGame(original.copy(winCounter = 5))
        val reloaded = repository.loadOrCreateGame()

        assertEquals(5, reloaded.winCounter)
        assertNull(reloaded.saveSession)
    }
}