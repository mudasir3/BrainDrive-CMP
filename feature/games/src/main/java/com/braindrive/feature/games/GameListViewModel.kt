package com.braindrive.feature.games

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.braindrive.core.domain.model.GameType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

data class GameListUiState(
    val games: List<GameItem> = listOf(
        GameItem(
            id = GameType.MATH,
            title = "Math It",
            description = "Identify the correct operation",
            icon = "➕"
        ),
        GameItem(
            id = GameType.CATEGORIZE_EDIBLE,
            title = "Categorize - Edible",
            description = "Sort edible items",
            icon = "🍎"
        ),
        GameItem(
            id = GameType.CATEGORIZE_CONSUMER,
            title = "Categorize - Consumer",
            description = "Sort consumer items",
            icon = "🛒"
        ),
        GameItem(
            id = GameType.CATEGORIZE_HUMAN,
            title = "Categorize - Human",
            description = "Sort human-related items",
            icon = "👤"
        ),
        GameItem(
            id = GameType.MEMORY_SECOND,
            title = "Memory Game 1",
            description = "Test your memory",
            icon = "🧩"
        ),
        GameItem(
            id = GameType.MEMORY_THIRD,
            title = "Memory Game 2",
            description = "Advanced memory challenge",
            icon = "🎯"
        )
    )
)

data class GameItem(
    val id: GameType,
    val title: String,
    val description: String,
    val icon: String
)

sealed class GameListIntent {
    data class NavigateToGame(val gameType: GameType) : GameListIntent()
}

@HiltViewModel
class GameListViewModel @Inject constructor() : ViewModel() {
    
    private val _uiState = MutableStateFlow(GameListUiState())
    val uiState: StateFlow<GameListUiState> = _uiState.asStateFlow()
    
    fun handleIntent(intent: GameListIntent) {
        when (intent) {
            is GameListIntent.NavigateToGame -> {
                // Navigation handled by composable
            }
        }
    }
}

