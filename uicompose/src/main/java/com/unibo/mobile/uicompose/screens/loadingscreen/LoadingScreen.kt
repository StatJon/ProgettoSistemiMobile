package com.unibo.mobile.uicompose.screens.loadingscreen

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.unibo.mobile.uicompose.R

@Composable
fun LoadingScreen(){
    Text(
        text = stringResource(R.string.loading_label)
    )
}