package com.unibo.mobile.data.repositories

import com.unibo.mobile.data.remote.api.DndApi
import com.unibo.mobile.data.remote.api.SafeApiCaller
import com.unibo.mobile.data.remote.mappers.MonsterDtoListToListMapper
import com.unibo.mobile.data.remote.mappers.MonsterToEnemyMapper
import com.unibo.mobile.data.remote.models.MonsterListResponseDto
import com.unibo.mobile.domain.models.ChallengeRating
import com.unibo.mobile.domain.models.CharacterEnemy
import com.unibo.mobile.domain.repositories.EnemyRepository

class EnemyRepositoryImpl(
    private val dndApi: DndApi,
    private val safeApiCaller: SafeApiCaller,
    private val monsterDtoListToListMapper: MonsterDtoListToListMapper,
    private val monsterToEnemyMapper: MonsterToEnemyMapper
) : EnemyRepository {

    override suspend fun getEnemyListByChallengeRating(challengeRating: ChallengeRating): List<String>? {
        println("DEBUG: API call with CR: $challengeRating")
        val monsterListResponseDto : MonsterListResponseDto =
            safeApiCaller.invoke { dndApi.getMonsterListByChallengeRating(challengeRating.crValue) }
                ?: return null
        println("DEBUG: API response: $monsterListResponseDto")
        val enemyList = monsterDtoListToListMapper.invoke(monsterListResponseDto.results)
        return enemyList
    }

    override suspend fun getEnemyByIndex(monsterIndex: String): CharacterEnemy? {
        val monsterDto =
            safeApiCaller.invoke { dndApi.getMonsterByIndex(monsterIndex) }
                ?: return null
        val enemy = monsterToEnemyMapper.invoke(monsterDto)
        return enemy
    }

}
