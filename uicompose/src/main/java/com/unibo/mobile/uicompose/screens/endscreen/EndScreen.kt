package com.unibo.mobile.uicompose.screens.endscreen


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.unibo.mobile.uicompose.R
import com.unibo.mobile.uicompose.common.LargeTitle
import com.unibo.mobile.uicompose.common.ScreenLayoutStandard
import com.unibo.mobile.uicompose.common.StandardButtonText
import com.unibo.mobile.uicompose.common.UiConstants

// --- --------------------------------------------------- ---//
// --- Main --- //
@Composable
fun EndScreen(
    isWon: Boolean,
    onBackToMenuTap: () -> Unit,
    isLandscape: Boolean,
    modifier: Modifier = Modifier
) {
    val titleRes = if (isWon) R.string.end_victory_title else R.string.end_defeat_title
    ScreenLayoutStandard(
        isLandscape = isLandscape,
        modifier = modifier,
        displayContent = {
            Column(modifier = Modifier.fillMaxSize()) {
                LargeTitle(stringResource(titleRes))
            }
        },
        inputContent = {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(
                    UiConstants.BUTTON_SPACING,
                    Alignment.Bottom
                )
            ) {
                StandardButtonText(
                    text = stringResource(R.string.back_to_menu_button),
                    onTap = onBackToMenuTap
                )
            }
        }
    )
}

// --- Main --- //
// --- --------------------------------------------------- ---//
// --- Preview --- //
@Preview(showBackground = true)
@Composable
private fun EndScreenPreview() {
    EndScreen(
        isWon = true,
        onBackToMenuTap = {},
        isLandscape = false
    )
}
// --- Preview --- //
// --- --------------------------------------------------- ---//