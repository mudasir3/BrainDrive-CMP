package com.braindrive.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.braindrive.core.domain.model.GameType
import com.braindrive.feature.games.GameListScreen
import com.braindrive.feature.games.math.MathGameScreen
import com.braindrive.feature.home.HomeScreen
import com.braindrive.feature.settings.SettingsScreen
import com.braindrive.feature.splash.SplashScreen

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Home : Screen("home")
    object GameList : Screen("game_list")
    object Settings : Screen("settings")

    // Updated to handle the route string statically
    data class MathGame(val playerName: String = "") : Screen(routeString) {
        companion object {
            const val routeString = "math_game/{playerName}"
            fun createRoute(playerName: String) = "math_game/$playerName"
        }
    }
}

@Composable
fun NavGraph(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route
    ) {
        composable(Screen.Splash.route) {
            SplashScreen(
                onNavigateToHome = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Splash.route) { inclusive = true }
                    }
                }
            )
        }

        composable(Screen.Home.route) {
            HomeScreen(
                onNavigateToGames = {
                    navController.navigate(Screen.GameList.route)
                },
                onNavigateToSettings = {
                    navController.navigate(Screen.Settings.route)
                }
            )
        }

        composable(Screen.GameList.route) {
            GameListScreen(
                onNavigateBack = {
                    navController.popBackStack()
                },
                onNavigateToGame = { gameType ->
                    when (gameType) {
                        GameType.MATH -> {
                            navController.navigate(Screen.MathGame.createRoute("Player"))
                        }
                        else -> {
                            // TODO: Navigate to other games
                        }
                    }
                }
            )
        }

        composable(Screen.Settings.route) {
            SettingsScreen(
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }

        composable(
            route = Screen.MathGame.routeString, // Accessing the companion constant
            arguments = listOf(
                navArgument("playerName") { // Use navArgument helper function
                    type = NavType.StringType
                    defaultValue = "Player"
                }
            )
        ) { backStackEntry ->
            val playerName = backStackEntry.arguments?.getString("playerName") ?: "Player"

            MathGameScreen(
                playerName = playerName,
                onNavigateBack = {
                    navController.popBackStack()
                },
                onGameComplete = { score ->
                    // TODO: Navigate to score screen
                    navController.popBackStack()
                }
            )
        }
    }
}
