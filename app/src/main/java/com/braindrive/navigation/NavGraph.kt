package com.braindrive.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.hilt.navigation.compose.hiltViewModel
import com.braindrive.core.domain.model.Difficulty
import com.braindrive.core.domain.model.GameType
import com.braindrive.feature.games.DifficultySelectionScreen
import com.braindrive.feature.games.GameListScreen
import com.braindrive.feature.games.GameResultScreen
import com.braindrive.feature.games.categorize.CategorizeGameScreen
import com.braindrive.feature.games.math.MathGameScreen
import com.braindrive.feature.games.memory.MemoryGameScreen
import com.braindrive.feature.home.HomeScreen
import com.braindrive.feature.settings.SettingsScreen
import com.braindrive.feature.splash.SplashScreen

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Home : Screen("home")
    object GameList : Screen("game_list")
    object Settings : Screen("settings")
    
    data class DifficultySelection(val gameType: GameType) : Screen("difficulty_selection/{gameType}") {
        companion object {
            const val routeString = "difficulty_selection/{gameType}"
            fun createRoute(gameType: GameType) = "difficulty_selection/${gameType.name}"
        }
    }
    
    data class MathGame(val playerName: String = "", val difficulty: Difficulty = Difficulty.MEDIUM) : Screen(routeString) {
        companion object {
            const val routeString = "math_game/{playerName}/{difficulty}"
            fun createRoute(playerName: String, difficulty: Difficulty) = "math_game/$playerName/${difficulty.name}"
        }
    }
    
    data class CategorizeGame(val gameType: GameType, val difficulty: Difficulty = Difficulty.MEDIUM) : Screen(routeString) {
        companion object {
            const val routeString = "categorize_game/{gameType}/{difficulty}"
            fun createRoute(gameType: GameType, difficulty: Difficulty) = "categorize_game/${gameType.name}/${difficulty.name}"
        }
    }
    
    data class MemoryGame(val gameType: GameType, val difficulty: Difficulty = Difficulty.MEDIUM) : Screen(routeString) {
        companion object {
            const val routeString = "memory_game/{gameType}/{difficulty}"
            fun createRoute(gameType: GameType, difficulty: Difficulty) = "memory_game/${gameType.name}/${difficulty.name}"
        }
    }
    
    data class GameResult(val gameType: GameType, val score: Int = 0) : Screen(routeString) {
        companion object {
            const val routeString = "game_result/{gameType}/{score}"
            fun createRoute(gameType: GameType, score: Int) = "game_result/${gameType.name}/$score"
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
                    navController.navigate(Screen.DifficultySelection.createRoute(gameType))
                }
            )
        }
        
        composable(
            route = Screen.DifficultySelection.routeString,
            arguments = listOf(
                navArgument("gameType") {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->
            val gameTypeStr = backStackEntry.arguments?.getString("gameType") ?: ""
            val gameType = try {
                GameType.valueOf(gameTypeStr)
            } catch (e: IllegalArgumentException) {
                GameType.MATH
            }
            
            val gameTitle = when (gameType) {
                GameType.MATH -> "Math It"
                GameType.CATEGORIZE_EDIBLE -> "Categorize - Edible"
                GameType.CATEGORIZE_CONSUMER -> "Categorize - Consumer"
                GameType.CATEGORIZE_HUMAN -> "Categorize - Human"
                GameType.MEMORY_SECOND -> "Memory Game 1"
                GameType.MEMORY_THIRD -> "Memory Game 2"
            }
            
            DifficultySelectionScreen(
                gameType = gameType,
                gameTitle = gameTitle,
                onNavigateBack = {
                    navController.popBackStack()
                },
                onDifficultySelected = { difficulty ->
                    when (gameType) {
                        GameType.MATH -> {
                            navController.navigate(Screen.MathGame.createRoute("Player", difficulty)) {
                                popUpTo(Screen.DifficultySelection.routeString) { inclusive = true }
                            }
                        }
                        GameType.CATEGORIZE_EDIBLE,
                        GameType.CATEGORIZE_CONSUMER,
                        GameType.CATEGORIZE_HUMAN -> {
                            navController.navigate(Screen.CategorizeGame.createRoute(gameType, difficulty)) {
                                popUpTo(Screen.DifficultySelection.routeString) { inclusive = true }
                            }
                        }
                        GameType.MEMORY_SECOND,
                        GameType.MEMORY_THIRD -> {
                            navController.navigate(Screen.MemoryGame.createRoute(gameType, difficulty)) {
                                popUpTo(Screen.DifficultySelection.routeString) { inclusive = true }
                            }
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
            route = Screen.MathGame.routeString,
            arguments = listOf(
                navArgument("playerName") {
                    type = NavType.StringType
                    defaultValue = "Player"
                },
                navArgument("difficulty") {
                    type = NavType.StringType
                    defaultValue = Difficulty.MEDIUM.name
                }
            )
        ) { backStackEntry ->
            val playerName = backStackEntry.arguments?.getString("playerName") ?: "Player"
            val difficultyStr = backStackEntry.arguments?.getString("difficulty") ?: Difficulty.MEDIUM.name
            val difficulty = try {
                Difficulty.valueOf(difficultyStr)
            } catch (e: IllegalArgumentException) {
                Difficulty.MEDIUM
            }

            MathGameScreen(
                playerName = playerName,
                difficulty = difficulty,
                onNavigateBack = {
                    navController.popBackStack()
                },
                onGameComplete = { score ->
                    navController.navigate(Screen.GameResult.createRoute(GameType.MATH, score)) {
                        popUpTo(Screen.MathGame.routeString) { inclusive = true }
                    }
                }
            )
        }
        
        composable(
            route = Screen.CategorizeGame.routeString,
            arguments = listOf(
                navArgument("gameType") {
                    type = NavType.StringType
                },
                navArgument("difficulty") {
                    type = NavType.StringType
                    defaultValue = Difficulty.MEDIUM.name
                }
            )
        ) { backStackEntry ->
            val gameTypeStr = backStackEntry.arguments?.getString("gameType") ?: ""
            val difficultyStr = backStackEntry.arguments?.getString("difficulty") ?: Difficulty.MEDIUM.name
            val gameType = try {
                GameType.valueOf(gameTypeStr)
            } catch (e: IllegalArgumentException) {
                GameType.CATEGORIZE_EDIBLE
            }
            val difficulty = try {
                Difficulty.valueOf(difficultyStr)
            } catch (e: IllegalArgumentException) {
                Difficulty.MEDIUM
            }

            CategorizeGameScreen(
                playerName = "Player",
                gameType = gameType,
                difficulty = difficulty,
                onNavigateBack = {
                    navController.popBackStack()
                },
                onGameComplete = { score ->
                    navController.navigate(Screen.GameResult.createRoute(gameType, score)) {
                        popUpTo(Screen.CategorizeGame.routeString) { inclusive = true }
                    }
                }
            )
        }
        
        composable(
            route = Screen.MemoryGame.routeString,
            arguments = listOf(
                navArgument("gameType") {
                    type = NavType.StringType
                },
                navArgument("difficulty") {
                    type = NavType.StringType
                    defaultValue = Difficulty.MEDIUM.name
                }
            )
        ) { backStackEntry ->
            val gameTypeStr = backStackEntry.arguments?.getString("gameType") ?: ""
            val difficultyStr = backStackEntry.arguments?.getString("difficulty") ?: Difficulty.MEDIUM.name
            val gameType = try {
                GameType.valueOf(gameTypeStr)
            } catch (e: IllegalArgumentException) {
                GameType.MEMORY_SECOND
            }
            val difficulty = try {
                Difficulty.valueOf(difficultyStr)
            } catch (e: IllegalArgumentException) {
                Difficulty.MEDIUM
            }

            MemoryGameScreen(
                playerName = "Player",
                gameType = gameType,
                difficulty = difficulty,
                onNavigateBack = {
                    navController.popBackStack()
                },
                onGameComplete = { score ->
                    navController.navigate(Screen.GameResult.createRoute(gameType, score)) {
                        popUpTo(Screen.MemoryGame.routeString) { inclusive = true }
                    }
                }
            )
        }
        
        composable(
            route = Screen.GameResult.routeString,
            arguments = listOf(
                navArgument("gameType") {
                    type = NavType.StringType
                },
                navArgument("score") {
                    type = NavType.IntType
                    defaultValue = 0
                }
            )
        ) { backStackEntry ->
            val gameTypeStr = backStackEntry.arguments?.getString("gameType") ?: ""
            val score = backStackEntry.arguments?.getInt("score") ?: 0
            val gameType = try {
                GameType.valueOf(gameTypeStr)
            } catch (e: IllegalArgumentException) {
                GameType.MATH
            }

            GameResultScreen(
                gameType = gameType,
                score = score,
                onPlayAgain = {
                    navController.navigate(Screen.DifficultySelection.createRoute(gameType)) {
                        popUpTo(Screen.GameResult.routeString) { inclusive = true }
                    }
                },
                onGoHome = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.GameResult.routeString) { inclusive = true }
                    }
                }
            )
        }
    }
}
