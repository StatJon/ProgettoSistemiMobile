package com.unibo.mobile.repositories

import com.unibo.mobile.data.repositories.PlayerClassRepositoryImpl
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertNull
import org.junit.Test

class PlayerClassRepositoryImplTest {

    private val repository = PlayerClassRepositoryImpl()

    @Test
    fun getAll() = runBlocking {
        val result = repository.getAllPlayerClasses()

        assert(result.isNotEmpty())
    }

    @Test
    fun getByNameMissing() = runBlocking {
        val result = repository.getPlayerClassByName("nonexistent")

        assertNull(result)
    }
}
