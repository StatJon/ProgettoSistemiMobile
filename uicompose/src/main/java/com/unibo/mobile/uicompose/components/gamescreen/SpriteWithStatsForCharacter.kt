package com.unibo.mobile.uicompose.components.gamescreen

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.unibo.mobile.domain.models.Character
import com.unibo.mobile.domain.models.CharacterEnemy
import com.unibo.mobile.domain.models.CharacterPlayer
import com.unibo.mobile.uicompose.R

@Composable
fun SpriteWithStatsForCharacter(
    character: Character,
    modifier: Modifier = Modifier
) {
    val spriteRes = when (character) {
        is CharacterPlayer -> R.drawable.cleric // TODO: PLACEHOLDER STATICO, SOSTITUIRE CON DINAMICO
        is CharacterEnemy -> R.drawable.humanoid // TODO: PLACEHOLDER STATICO, SOSTITUIRE CON DINAMICO
    }
    val stats = buildList {
        add(stringResource(R.string.name_label, character.characterData.name))
        add(
            stringResource(
                R.string.hp_label,
                character.characterData.currentHealthPoints,
                character.characterData.maxHealthPoints
            )
        )
        add(stringResource(R.string.ac_label, character.characterData.armorClass))
        if (character is CharacterPlayer) {
            add(stringResource(R.string.level_label, character.level))
        } else if (character is CharacterEnemy) {
            add(stringResource(R.string.type_label, character.enemyType))
            add(stringResource(R.string.cr_label, character.challengeRating))
        }
        if (character is CharacterPlayer) {
            add(
                stringResource(
                    R.string.mp_label,
                    character.currentManaPoints,
                    character.maxManaPoints
                )
            )
        }
    }
    SpriteWithStats(
        spritePainter = painterResource(spriteRes),
        spriteName = character.characterData.name,
        infoList = stats,
        modifier = modifier
    )
}