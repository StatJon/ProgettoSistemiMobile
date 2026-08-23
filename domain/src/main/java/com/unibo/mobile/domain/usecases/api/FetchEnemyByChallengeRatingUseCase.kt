package com.unibo.mobile.domain.usecases.api

import com.unibo.mobile.domain.models.ChallengeRating
import com.unibo.mobile.domain.models.CharacterEnemy

interface FetchEnemyByChallengeRatingUseCase {
    suspend fun invoke(challengeRating: ChallengeRating): CharacterEnemy
}

//Internamente la repository restituisce una lista poi viene deciso solo un Enemy