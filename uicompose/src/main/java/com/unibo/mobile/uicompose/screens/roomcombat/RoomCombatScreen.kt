package com.unibo.mobile.uicompose.screens.roomcombat

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.unibo.mobile.domain.model.ability.Ability
import com.unibo.mobile.domain.model.ability.AbilityType
import com.unibo.mobile.domain.model.ability.Dice
import com.unibo.mobile.domain.model.combat.CombatLogEntry
import com.unibo.mobile.domain.model.combat.CombatOutcome
import com.unibo.mobile.domain.model.combat.CombatState
import com.unibo.mobile.domain.model.entity.Ally
import com.unibo.mobile.domain.model.entity.CombatEntity
import com.unibo.mobile.domain.model.entity.Enemy
import com.unibo.mobile.domain.model.entity.PlayerClass
import com.unibo.mobile.uicompose.R
import com.unibo.mobile.uicompose.common.ScreenLayoutStandard
import com.unibo.mobile.uicompose.common.StandardButtonComposable
import com.unibo.mobile.uicompose.common.StandardCardComposable
import com.unibo.mobile.uicompose.common.UiConstants

// --- --------------------------------------------------- ---//
// --- Main --- //
@Composable
fun RoomCombatScreen(
    isLandscape: Boolean,
    combatState: CombatState,
    abilityList: List<Ability>,
    player: Ally,
    onAbilityAndEntityConfirmed: (Ability, CombatEntity) -> Unit,
    modifier: Modifier = Modifier
) {

    var selectedAbilityId by remember { mutableStateOf<String?>(null) }

    val onAbilityTap: (Ability) -> Unit = { ability ->
        selectedAbilityId = ability.abilityId
    }

    val onEntityTap: (CombatEntity) -> Unit = { entity ->
        abilityList.firstOrNull { it.abilityId == selectedAbilityId }?.let { ability ->
            onAbilityAndEntityConfirmed(ability, entity)
            selectedAbilityId = null
        }
    }

    ScreenLayoutStandard(
        isLandscape = isLandscape,
        modifier = modifier,
        displayContent = {Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(UiConstants.BUTTON_SPACING)
        ) {
            FightUi(
                combatState = combatState,
                onEntityTap = onEntityTap,
                modifier = Modifier.weight(0.7f)
            )
            LogUi(
                combatState = combatState,
                modifier = Modifier.weight(0.3f)
            )
        }
        },
        inputContent = {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(UiConstants.BUTTON_SPACING)
            ) {
                PlayerStatUi(player, onEntityTap)
                LazyColumn(
                    modifier = Modifier
                        .weight(1f)
                        .background(
                            color = MaterialTheme.colorScheme.surfaceVariant,
                            shape = RoundedCornerShape(
                                UiConstants.ROUNDED_CORNER_RADIUS
                            )
                        ),
                    verticalArrangement = Arrangement.spacedBy(UiConstants.BUTTON_SPACING)
                ) {
                    items(abilityList) { ability ->
                        AbilityButton(ability = ability, onTap = { onAbilityTap(ability) })
                    }
                }
            }
        }
    )
}

// --- Main --- //
// --- --------------------------------------------------- ---//
// --- Components Composables ---//

@Composable
private fun FightUi(
    combatState: CombatState,
    onEntityTap: (CombatEntity) -> Unit,
    modifier: Modifier = Modifier
) {
    StandardCardComposable(modifier = modifier) {
        Text(stringResource(R.string.combat_screen_label))
        combatState.getAllEnemies().forEach { enemy ->
            StandardButtonComposable(
                onTap = { onEntityTap(enemy) },
                enabled = enemy.isAlive()
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(enemy.displayName)
                    Text(stringResource(R.string.entity_hp_label, enemy.currentHp, enemy.maxHp))
                }
            }
        }
    }
}

@Composable
private fun LogUi(combatState: CombatState, modifier: Modifier = Modifier) {
    StandardCardComposable(modifier = modifier) {
        Text(stringResource(R.string.log_section_label))
        LazyColumn() {
            items(combatState.log) { logEntry ->
                Text("PLACEHOLDER LOG")
            }

        }
    }
}

@Composable
private fun PlayerStatUi(player: Ally, onEntityTap: (CombatEntity) -> Unit) {
    StandardButtonComposable(onTap = { onEntityTap(player) }) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(player.displayName)
            Text(stringResource(R.string.entity_hp_label, player.currentHp, player.maxHp))
            Text(stringResource(R.string.char_ap_label, player.currentAp, player.maxAp))
            Text(stringResource(R.string.char_mp_label, player.currentMp, player.maxMp))
        }
    }
}

@Composable
private fun AbilityButton(ability: Ability, onTap: () -> Unit) {
    StandardButtonComposable(onTap = onTap) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = ability.displayName,
                modifier = Modifier.weight(1f)
            )
            Column(
                modifier = Modifier.weight(0.5f),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = abilityTypeLabel(ability))
                Text(text = abilityTargetsLabel(ability.abilityType))
            }
            Column(
                modifier = Modifier.weight(0.7f),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally

            ) {
                Text(text = abilityApCostLabel(ability))
                Text(text = abilityMpCostLabel(ability))
            }
        }
    }
}

@Composable
private fun abilityTypeLabel(ability: Ability): String {
    return when (ability.abilityType) {
        AbilityType.DAMAGE_SINGLE, AbilityType.DAMAGE_AOE ->
            stringResource(R.string.damage_label, ability.diceCount, ability.diceType.dice)

        AbilityType.HEAL_SINGLE, AbilityType.HEAL_AOE ->
            stringResource(R.string.heal_label, ability.diceCount, ability.diceType.dice)
    }
}

@Composable
private fun abilityTargetsLabel(type: AbilityType): String {
    return when (type) {
        AbilityType.DAMAGE_SINGLE, AbilityType.HEAL_SINGLE -> stringResource(R.string.one_target_label)
        AbilityType.DAMAGE_AOE, AbilityType.HEAL_AOE -> stringResource(R.string.all_target_label)
    }
}

@Composable
private fun abilityApCostLabel(ability: Ability): String {
    return when (ability.actionCost) {
        2 -> stringResource(R.string.full_action_label)
        1 -> stringResource(R.string.bonus_action_label)
        else -> stringResource(R.string.full_action_label) // Non dovrebbe mai accadere, è per silenziare il when
    }
}

@Composable
private fun abilityMpCostLabel(ability: Ability): String {
    return stringResource(R.string.cost_label, ability.level)
}

// --- Components Composables ---//
// --- --------------------------------------------------- ---//
// --- Preview --- //
@Preview(showBackground = true)
@Composable
private fun RoomCombatPreview() {

    val fakeCombatState = object : CombatState {
        override val turnOrder: List<CombatEntity> = emptyList()
        override val turnIndex: Int = 0
        override val log: List<CombatLogEntry> = emptyList()
        override val status: CombatOutcome = CombatOutcome.ONGOING

        override fun getActiveEntity(): CombatEntity = turnOrder.first()
        override fun getAllEnemies(): List<Enemy> = emptyList()
        override fun getAllAllies(): List<Ally> = emptyList()
    }

    val fakeAlly = object : Ally {
        override val currentMp = 8
        override val maxMp = 8
        override val playerClass = object : PlayerClass {
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
        override val playerExp = 0
        override fun getCurrentLevel(): Int = 1
        override fun changeMp(amount: Int): Ally = this
        override val entityId = "player1"
        override val displayName = "Suor Mazzate"
        override val currentHp = 16
        override val maxHp = 16
        override val currentAp = 2
        override val maxAp = 2
        override val armorClass = 12
        override val strength = 10
        override val dexterity = 14
        override val constitution = 16
        override val intelligence = 8
        override val wisdom = 16
        override val charisma = 8
        override val abilities: List<Ability> = emptyList()
        override fun changeHp(amount: Int): CombatEntity = this
        override fun changeAp(amount: Int): CombatEntity = this
    }

    val fakeAbilities: List<Ability> = listOf(
        object : Ability {
            override val abilityId = "guiding_bolt"
            override val displayName = "Guiding Bolt"
            override val actionCost = 2
            override val level = 3
            override val abilityType = AbilityType.DAMAGE_AOE
            override val diceCount = 4
            override val diceType = Dice.D6
            override val flatExtraAmount = 0
        },
        object : Ability {
            override val abilityId = "healing_word"
            override val displayName = "Healing Word"
            override val actionCost = 1
            override val level = 2
            override val abilityType = AbilityType.HEAL_SINGLE
            override val diceCount = 2
            override val diceType = Dice.D6
            override val flatExtraAmount = 0
        },
        object : Ability {
            override val abilityId = "guiding_bolt"
            override val displayName = "Guiding Bolt"
            override val actionCost = 2
            override val level = 3
            override val abilityType = AbilityType.DAMAGE_AOE
            override val diceCount = 4
            override val diceType = Dice.D6
            override val flatExtraAmount = 0
        }
    )

    RoomCombatScreen(
        isLandscape = false,
        combatState = fakeCombatState,
        abilityList = fakeAbilities,
        player = fakeAlly,
        onAbilityAndEntityConfirmed = { _, _ -> },
    )
}

// --- Preview --- //
// --- --------------------------------------------------- ---//