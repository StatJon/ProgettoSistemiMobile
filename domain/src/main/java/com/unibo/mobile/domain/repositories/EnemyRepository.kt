package com.unibo.mobile.domain.repositories

import com.unibo.mobile.domain.models.ChallengeRating
import com.unibo.mobile.domain.models.CharacterEnemy

interface EnemyRepository {
    suspend fun getEnemyListByChallengeRating (challengeRating: ChallengeRating) : List<String>?
    suspend fun getEnemyByIndex (monsterIndex: String) : CharacterEnemy?
}