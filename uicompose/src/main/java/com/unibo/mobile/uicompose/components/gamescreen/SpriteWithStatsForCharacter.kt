package com.unibo.mobile.uicompose.components.gamescreen

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.unibo.mobile.domain.models.Character
import com.unibo.mobile.domain.models.CharacterEnemy
import com.unibo.mobile.domain.models.CharacterPlayer
import com.unibo.mobile.domain.models.EnemyType
import com.unibo.mobile.uicompose.R

@Composable
fun SpriteWithStatsForCharacter(
    character: Character,
    modifier: Modifier = Modifier
) {
    val spriteRes = when (character) {
        is CharacterPlayer -> getDrawableResForPlayerClass(character.playerClass.classIndex)
        is CharacterEnemy -> getDrawableResForEnemyType(character.enemyType)
    }

    val stats = when (character) {
        is CharacterPlayer -> listOf(
            stringResource(R.string.name_label, character.characterData.name),
            stringResource(
                R.string.hp_label,
                character.characterData.currentHealthPoints,
                character.characterData.maxHealthPoints
            ),
            stringResource(R.string.ac_label, character.characterData.armorClass),
            stringResource(R.string.level_label, character.level),
            stringResource(
                R.string.mp_label,
                character.currentManaPoints,
                character.maxManaPoints
            )
        )
        is CharacterEnemy -> listOf(
            stringResource(R.string.name_label, character.characterData.name),
            stringResource(
                R.string.hp_label,
                character.characterData.currentHealthPoints,
                character.characterData.maxHealthPoints
            ),
            stringResource(R.string.ac_label, character.characterData.armorClass),
            stringResource(R.string.type_label, character.enemyType),
            stringResource(R.string.cr_label, character.challengeRating)
        )
    }

    SpriteWithStats(
        spritePainter = painterResource(spriteRes),
        spriteName = character.characterData.name,
        infoList = stats,
        modifier = modifier
    )
}

private fun getDrawableResForPlayerClass(classIndex: String): Int = when (classIndex) {
    "cleric" -> R.drawable.cleric
    "warlock" -> R.drawable.warlock
    else -> R.drawable.humanoid
}

private fun getDrawableResForEnemyType(enemyType: EnemyType): Int = when (enemyType) {
    EnemyType.ABERRATION -> R.drawable.aberration
    EnemyType.BEAST -> R.drawable.beast
    EnemyType.CELESTIAL -> R.drawable.celestial
    EnemyType.CONSTRUCT -> R.drawable.construct
    EnemyType.DRAGON -> R.drawable.dragon
    EnemyType.ELEMENTAL -> R.drawable.elemental
    EnemyType.FEY -> R.drawable.fey
    EnemyType.FIEND -> R.drawable.fiend
    EnemyType.GIANT -> R.drawable.giant
    EnemyType.HUMANOID -> R.drawable.humanoid
    EnemyType.MONSTROSITY -> R.drawable.monstrosity
    EnemyType.OOZE -> R.drawable.ooze
    EnemyType.PLANT -> R.drawable.plant
    EnemyType.UNDEAD -> R.drawable.undead
}