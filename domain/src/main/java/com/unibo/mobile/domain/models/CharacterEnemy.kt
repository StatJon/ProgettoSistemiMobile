package com.unibo.mobile.domain.models

data class CharacterEnemy(
    val challengeRating: ChallengeRating,
    val enemyType: EnemyType,
    override val characterData: CharacterData,
) : Character
