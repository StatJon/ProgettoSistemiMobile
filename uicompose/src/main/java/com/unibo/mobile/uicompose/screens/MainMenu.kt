package com.unibo.mobile.uicompose.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import com.unibo.mobile.uicompose.common.BasicButton
import com.unibo.mobile.uicompose.common.BasicLabel

@Composable
fun MainMenu(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
    ){
        BasicLabel(text = "hello")
        TODO("SOPPRESSIONE, SOSTITUIRE CON DATI VERI")
    }
}