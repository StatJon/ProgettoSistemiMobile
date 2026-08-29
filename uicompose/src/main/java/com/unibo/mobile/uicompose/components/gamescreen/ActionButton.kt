package com.unibo.mobile.uicompose.components.gamescreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import com.unibo.mobile.uicompose.R
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.unibo.mobile.domain.models.Ability
import com.unibo.mobile.domain.models.AbilityDamage
import com.unibo.mobile.domain.models.AbilityHeal
import com.unibo.mobile.domain.models.ActionCost
import com.unibo.mobile.domain.models.DicesToRoll

@Composable
fun ActionButton(
    ability: Ability,
    onClick: () -> Unit,
    lockUi: Boolean,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        enabled = !lockUi
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly

        ) {
            Text(ability.name)
            Text(
                text = if (ability is AbilityHeal) {
                    stringResource(R.string.heal_label)
                } else {
                    stringResource(R.string.damage_label)
                }
            )
            Text(text = ability.dicesToRoll.diceNumber.toString() + "d" + ability.dicesToRoll.diceFaces.toString())
            Text(text = stringResource(R.string.mana_label) + ability.manaCost.toString())
        }
    }
}

@Preview
@Composable
fun ActionButtonPreview() {
    ActionButton(
        ability = AbilityDamage(
            name = "TestAbilityName",
            index = "test",
            level = 1,
            isAoe = false,
            actionCost = ActionCost.ACTION,
            manaCost = 1,
            dicesToRoll = DicesToRoll(1, 6),
            requiresHitRoll = true
        ),
        onClick = {},
        lockUi = false,

        )
}