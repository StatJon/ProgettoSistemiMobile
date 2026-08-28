package com.unibo.mobile.uicompose.components.gamescreen

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
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
        add("Name: ${character.characterData.name}")
        if (character is CharacterPlayer) {
            add("Level: ${character.level}")
        }else if (character is CharacterEnemy){
            add("Type: ${character.enemyType}")
            add("CR: ${character.challengeRating}")
        }
        add("AC: ${character.characterData.armorClass}")
        add("HP: ${character.characterData.currentHealthPoints}/${character.characterData.maxHealthPoints}")
        if (character is CharacterPlayer) {
            add("MP: ${character.currentManaPoints}/${character.maxManaPoints}")
        }
    }
    SpriteWithStats(
        spritePainter = painterResource(spriteRes),
        spriteName = character.characterData.name,
        infoList = stats,
        modifier = modifier
    )
}