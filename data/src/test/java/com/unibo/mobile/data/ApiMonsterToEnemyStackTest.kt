package com.unibo.mobile.data

import com.unibo.mobile.data.local.dao.SaveGameDao
import com.unibo.mobile.data.local.entities.SaveGameEntity
import com.unibo.mobile.data.models.entity.EnemyTypeImpl
import com.unibo.mobile.data.remote.api.RetrofitClient
import com.unibo.mobile.data.remote.mapper.toEnemyOrNull
import com.unibo.mobile.data.remote.models.monster.MonsterDto
import com.unibo.mobile.data.repositories.LocalRepositoryImpl
import kotlinx.coroutines.runBlocking
import org.junit.Test

class ApiMonsterToEnemyStackTest {

    @Test
    fun testMonsterHumanoidToEnemyMockupList(): Unit = runBlocking {
        val client = RetrofitClient()
        val mockupEnemyTypes = listOf(EnemyTypeImpl(enemyTypeId = "humanoid", displayName = "enemy_type_humanoid"))
        val monsterDto: MonsterDto = client.dndService.getMonster("acolyte")
        val enemy = monsterDto.toEnemyOrNull(mockupEnemyTypes)
        println(enemy)
    }

    class FakeSaveGameDao : SaveGameDao {
        override suspend fun getSaveGame(): SaveGameEntity? = null
        override suspend fun insertOrUpdate(entity: SaveGameEntity) {}
    }

    @Test
    fun testMonsterHumanoidToEnemyRepositoryList(): Unit = runBlocking {
        val client = RetrofitClient()
        val enemyTypeList = LocalRepositoryImpl(
            saveGameDao = FakeSaveGameDao()
        ).getEnemyTypes()
        val monsterDto: MonsterDto = client.dndService.getMonster("acolyte")
        val enemy = monsterDto.toEnemyOrNull(enemyTypeList)
        println(enemy)
    }
}