package com.unibo.mobile.uicompose.components.gamescreen

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import com.unibo.mobile.uicompose.R
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.unibo.mobile.domain.models.Ability
import com.unibo.mobile.domain.models.AbilityDamage
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
        Text(ability.name)
    }
}

@Preview
@Composable
fun ActionButtonPreview() {
    ActionButton(
        ability = AbilityDamage(
            name = "Test",
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