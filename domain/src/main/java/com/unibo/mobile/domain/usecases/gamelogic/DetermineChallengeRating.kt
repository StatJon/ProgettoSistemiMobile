package com.unibo.mobile.domain.usecases.gamelogic

import com.unibo.mobile.domain.models.ChallengeRating

interface DetermineChallengeRatingUseCase {
    fun invoke(dungeonIndex: Int): ChallengeRating
}

class DetermineChallengeRatingUseCaseImpl : DetermineChallengeRatingUseCase {
    override fun invoke(dungeonIndex: Int): ChallengeRating {
        return ChallengeRating.entries[dungeonIndex]
    }
}