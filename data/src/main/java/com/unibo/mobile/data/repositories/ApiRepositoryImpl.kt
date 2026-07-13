package com.unibo.mobile.data.repositories

import com.unibo.mobile.data.remote.api.DndApi
import com.unibo.mobile.data.remote.mapper.toEnemyOrNull
import com.unibo.mobile.data.remote.models.monster.MonsterDto
import com.unibo.mobile.data.remote.models.monster.MonsterListDto
import com.unibo.mobile.domain.model.ability.Ability
import com.unibo.mobile.domain.model.dungeon.ChallengeRating
import com.unibo.mobile.domain.model.dungeon.RoomTypeCombat
import com.unibo.mobile.domain.model.entity.Enemy
import com.unibo.mobile.domain.model.entity.EnemyType
import com.unibo.mobile.domain.model.entity.PlayerClass
import com.unibo.mobile.domain.repositories.ApiRepository
import com.unibo.mobile.domain.repositories.LocalRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope

class ApiRepositoryImpl(
    private val dndApi: DndApi,
    private val localRepository: LocalRepository
) : ApiRepository {

    override suspend fun getEnemies(room: RoomTypeCombat): List<Enemy> {
        val crValues = ChallengeRating.entries.filter {
            it.challengeRatingValue in room.minEnemyCr.challengeRatingValue..room.maxTotalCr.challengeRatingValue
        }
        val crQueryList: String = crValues.joinToString(
            separator = ",",
            transform = { it.challengeRatingValue.toString() })
        println(crQueryList)
        val monsterListDto: MonsterListDto = dndApi.getMonstersChallengeRating(crQueryList)
        val indices: List<String> = monsterListDto.results.map { it.index }
        val monsterDtoList: List<MonsterDto> = coroutineScope {
            indices.map { index ->
                async {
                    println("Fetching: $index")
                    try {
                        dndApi.getMonster(index)
                    } catch (e: Exception) {
                        null
                    }
                }
            }.awaitAll().filterNotNull()
        }
        val enemyTypeList = localRepository.getEnemyTypes()
        val enemies: List<Enemy> = monsterDtoList.mapNotNull { it.toEnemyOrNull(enemyTypeList) }

        return enemies
    }

    override suspend fun getAbilitiesEnemy(enemy: EnemyType): List<Ability> {
        TODO("Funzione di gioco extra, aggiunge altre spell ai nemici, non obbligatorio per prima versione")
    }

    override suspend fun getAbilitiesPlayerCharacter(
        playerClass: PlayerClass,
        level: Int
    ): List<Ability> {
        TODO("Not yet implemented")
    }

}