package com.unibo.mobile.uicompose

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.unibo.mobile.uicompose.components.common.UiConstants
import com.unibo.mobile.uicompose.screens.EndScreen
import com.unibo.mobile.uicompose.screens.GameScreen
import com.unibo.mobile.uicompose.screens.MainMenu

/**
 * Contains the UI and manages navigation of the Screens to show
 */

private object Routes {
    const val MENU = "menu"
    const val GAME = "game"
    const val END_SCREEN = "end/{isWon}"
    fun endScreen(isWon: Boolean) = "end/$isWon"
}

@Composable
fun ScreenManager() {
    val navController = rememberNavController()

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .padding(UiConstants.EDGE_BASE_PADDING)
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            NavHost(navController = navController, startDestination = Routes.MENU) {

                composable(Routes.MENU) {
                    MainMenu(
                        onNavigateToGame = {
                            navController.navigate(Routes.GAME) {
                                popUpTo(Routes.MENU) { inclusive = true }
                            }
                        }
                    )
                }

                composable(Routes.GAME) {
                    GameScreen(
                        onNavigateToMenu = {
                            navController.navigate(Routes.MENU) {
                                popUpTo(Routes.GAME) { inclusive = true }
                            }
                        },
                        onNavigateToEndScreen = { isWon ->
                            navController.navigate(Routes.endScreen(isWon)) {
                                popUpTo(Routes.GAME) { inclusive = true }
                            }
                        }
                    )
                }

                composable(
                    route = Routes.END_SCREEN,
                    arguments = listOf(navArgument("isWon") { type = NavType.BoolType })
                ) { backStackEntry ->
                    val isWon = backStackEntry.arguments?.getBoolean("isWon") ?: false
                    EndScreen(
                        isWon = isWon,
                        onNavigateToMenu = {
                            navController.navigate(Routes.MENU) {
                                popUpTo(Routes.END_SCREEN) { inclusive = true }
                            }
                        }
                    )
                }
            }
        }
    }
}