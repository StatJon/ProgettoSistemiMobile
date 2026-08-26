package com.unibo.mobile.domain.usecases.api

import com.unibo.mobile.domain.models.Ability
import com.unibo.mobile.domain.models.ChallengeRating
import com.unibo.mobile.domain.models.CharacterData
import com.unibo.mobile.domain.models.CharacterEnemy
import com.unibo.mobile.domain.repositories.AbilityRepository
import com.unibo.mobile.domain.repositories.EnemyRepository

interface FetchEnemyByChallengeRatingUseCase {
    suspend fun invoke(challengeRating: ChallengeRating): CharacterEnemy
}

//Internamente la repository restituisce una lista poi viene deciso solo un Enemy
class FetchEnemyByChallengeRatingUseCaseImpl(
    private val enemyRepository: EnemyRepository,
) : FetchEnemyByChallengeRatingUseCase {
    override suspend fun invoke(challengeRating: ChallengeRating): CharacterEnemy {
        println("DEBUG: Fetching enemy for CR: $challengeRating")
        val enemyIndexList: List<String> =
            enemyRepository.getEnemyListByChallengeRating(challengeRating)
                ?: error("Error: Monsters not found by filter ")
        val enemyIndex: String = selectEnemyFromList(enemyIndexList)
            ?: error("Error: No monsters to choose from")
        val enemy = enemyRepository.getEnemyByIndex(enemyIndex)
            ?: error("Error: Monster not found by index")
        println("DEBUG: Enemy from repo: $enemy")
        return enemy
    }

    private fun selectEnemyFromList(enemyIndexList: List<String>): String? {
        return enemyIndexList.randomOrNull()
    }
}