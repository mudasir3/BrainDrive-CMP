package com.braindrive.feature.games

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.braindrive.core.domain.model.GameType
import com.braindrive.core.domain.usecase.GetHighScoreUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class GameResultUiState(
    val highScore: Int? = null,
    val isLoading: Boolean = true
)

@HiltViewModel
class GameResultViewModel @Inject constructor(
    private val getHighScoreUseCase: GetHighScoreUseCase
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(GameResultUiState())
    val uiState: StateFlow<GameResultUiState> = _uiState.asStateFlow()
    
    fun loadHighScore(gameType: GameType) {
        viewModelScope.launch {
            val highScoreEntity = getHighScoreUseCase(gameType)
            _uiState.value = _uiState.value.copy(
                highScore = highScoreEntity?.score,
                isLoading = false
            )
        }
    }
}

