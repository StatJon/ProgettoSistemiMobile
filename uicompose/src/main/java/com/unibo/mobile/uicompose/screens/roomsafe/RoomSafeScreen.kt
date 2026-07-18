package com.unibo.mobile.uicompose.screens.roomsafe

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.unibo.mobile.domain.model.ability.Ability
import com.unibo.mobile.domain.model.dungeon.RoomAction
import com.unibo.mobile.domain.model.entity.PlayerClass
import com.unibo.mobile.domain.model.save.PlayerCharacter
import com.unibo.mobile.uicompose.R
import com.unibo.mobile.uicompose.common.LargeTitle
import com.unibo.mobile.uicompose.common.ScreenLayoutStandard
import com.unibo.mobile.uicompose.common.StandardButtonText
import com.unibo.mobile.uicompose.common.StandardCardComposable
import com.unibo.mobile.uicompose.common.UiConstants

@Composable
fun RoomSafeScreen(
    isLandscape: Boolean,
    player: PlayerCharacter,
    availableActions: List<RoomAction>,
    onRoomActionTap: (RoomAction) -> Unit,
    onEnterNextRoomTap: () -> Unit,
    modifier: Modifier = Modifier
) {
    ScreenLayoutStandard(
        isLandscape = isLandscape,
        modifier = modifier,
        displayContent = {
            Column(modifier = Modifier.fillMaxSize()) {
                LargeTitle(stringResource(R.string.room_safe_title))
                CharacterCard(player)
            }
        },
        inputContent = {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(UiConstants.BUTTON_SPACING, Alignment.Bottom)
            ) {
                ActionButtons(availableActions, onRoomActionTap)
                EnterNextRoomButton(onEnterNextRoomTap)
            }
        }
    )
}
// --- Main --- //
// --- --------------------------------------------------- ---//
// --- Components Composables ---//
@Composable
private fun CharacterCard(player: PlayerCharacter) {
    StandardCardComposable {
        Text(stringResource(R.string.char_class_label, player.characterClass.displayName, player.characterLevel))
        Text(stringResource(R.string.char_hp_label, player.characterHp, player.characterMaxHp))
        Text(stringResource(R.string.char_mp_label, player.characterMp, player.characterMaxMp))
        Text(stringResource(R.string.char_exp_label, player.characterExp))
    }
}

@Composable
private fun ActionButtons(
    availableActions: List<RoomAction>,
    onTap: (RoomAction) -> Unit
) {
    availableActions.forEach { roomAction ->
        StandardButtonText(
            text = stringResource(roomAction.toLabelRes()),
            onTap = { onTap(roomAction) }
        )
    }
}

@Composable
private fun EnterNextRoomButton(onTap: () -> Unit) {
    StandardButtonText(text = stringResource(R.string.enter_next_room_button), onTap = onTap)
}

@StringRes
private fun RoomAction.toLabelRes(): Int = when (this) {
    RoomAction.REST -> R.string.action_rest
    RoomAction.LEVEL_UP -> R.string.action_levelup
}
// --- Components Composables ---//
// --- --------------------------------------------------- ---//
// --- Preview --- //
@Preview(showBackground = true)
@Composable
private fun RoomSafeScreenPreview() {
    val fakePlayer = object : PlayerCharacter {
        override val characterClass = object : PlayerClass {
            override val classId = "cleric"
            override val displayName = "Cleric"
            override val unlockCountRequired = 0
            override val strength = 10
            override val dexterity = 14
            override val constitution = 16
            override val intelligence = 8
            override val wisdom = 16
            override val charisma = 8
        }
        override val characterHp = 12
        override val characterMaxHp = 16
        override val characterMp = 5
        override val characterMaxMp = 8
        override val characterLevel = 2
        override val characterExp = 150
        override val abilityList = emptyList<Ability>()
        override fun changeHp(amount: Int) = this
        override fun changeMp(amount: Int) = this
        override fun addMaxHp(amount: Int) = this
        override fun addMaxMp(amount: Int) = this
        override fun addAbilities(abilityList: List<Ability>) = this
        override fun setLevel(newLevel: Int) = this
        override fun addExp(amount: Int) = this
    }

    RoomSafeScreen(
        isLandscape = false,
        player = fakePlayer,
        availableActions = listOf(RoomAction.REST, RoomAction.LEVEL_UP),
        onRoomActionTap = {},
        onEnterNextRoomTap = {}
    )
}
// --- Preview --- //
// --- --------------------------------------------------- ---//