package com.braindrive.feature.games.memory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.braindrive.core.domain.model.Difficulty
import com.braindrive.core.domain.model.GameType
import com.braindrive.core.domain.model.MemoryCard
import com.braindrive.core.domain.model.MemoryGameState
import com.braindrive.core.domain.usecase.GenerateMemoryGameUseCase
import com.braindrive.core.domain.usecase.SaveGameScoreUseCase
import com.braindrive.core.domain.usecase.GetUserPreferencesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

data class MemoryGameUiState(
    val cards: List<MemoryCard> = emptyList(),
    val flippedCards: List<Int> = emptyList(),
    val matchedPairs: Int = 0,
    val moves: Int = 0,
    val timeElapsed: Long = 0L,
    val isCompleted: Boolean = false,
    val score: Int = 0,
    val playerName: String = "",
    val difficulty: Difficulty = Difficulty.MEDIUM,
    val gameType: GameType = GameType.MEMORY_SECOND
)

sealed class MemoryGameIntent {
    data class StartGame(val difficulty: Difficulty, val useNumbers: Boolean) : MemoryGameIntent()
    data class FlipCard(val index: Int) : MemoryGameIntent()
    object FinishGame : MemoryGameIntent()
}

@HiltViewModel
class MemoryGameViewModel @Inject constructor(
    private val generateMemoryGameUseCase: GenerateMemoryGameUseCase,
    private val saveGameScoreUseCase: SaveGameScoreUseCase,
    private val getUserPreferencesUseCase: GetUserPreferencesUseCase
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(MemoryGameUiState())
    val uiState: StateFlow<MemoryGameUiState> = _uiState.asStateFlow()
    
    private var timerJob: Job? = null
    private var startTime: Long = 0L
    
    fun handleIntent(intent: MemoryGameIntent) {
        when (intent) {
            is MemoryGameIntent.StartGame -> startGame(intent.difficulty, intent.useNumbers)
            is MemoryGameIntent.FlipCard -> flipCard(intent.index)
            is MemoryGameIntent.FinishGame -> finishGame()
        }
    }
    
    private fun startGame(difficulty: Difficulty, useNumbers: Boolean) {
        viewModelScope.launch {
            val preferences = getUserPreferencesUseCase().first()
            val cards = generateMemoryGameUseCase(difficulty, useNumbers)
            startTime = System.currentTimeMillis()
            
            _uiState.value = MemoryGameUiState(
                cards = cards,
                playerName = preferences.userName.ifEmpty { "Player" },
                difficulty = difficulty,
                gameType = if (useNumbers) GameType.MEMORY_THIRD else GameType.MEMORY_SECOND
            )
            
            startTimer()
        }
    }
    
    private fun startTimer() {
        timerJob?.cancel()
        timerJob = viewModelScope.launch {
            while (!_uiState.value.isCompleted) {
                delay(100)
                val elapsed = System.currentTimeMillis() - startTime
                _uiState.value = _uiState.value.copy(timeElapsed = elapsed)
            }
        }
    }
    
    private fun flipCard(index: Int) {
        val currentState = _uiState.value
        val card = currentState.cards.getOrNull(index) ?: return
        
        // Don't flip if already matched or already flipped
        if (card.isMatched || card.isFlipped) return
        
        // Don't flip if already 2 cards are flipped
        if (currentState.flippedCards.size >= 2) return
        
        val updatedCards = currentState.cards.toMutableList()
        updatedCards[index] = card.flip()
        
        val newFlippedCards = currentState.flippedCards + index
        
        _uiState.value = currentState.copy(
            cards = updatedCards,
            flippedCards = newFlippedCards,
            moves = currentState.moves + 1
        )
        
        // Check for match if 2 cards are flipped
        if (newFlippedCards.size == 2) {
            checkForMatch(newFlippedCards[0], newFlippedCards[1])
        }
    }
    
    private fun checkForMatch(index1: Int, index2: Int) {
        viewModelScope.launch {
            delay(1000) // Show cards for 1 second
            
            val card1 = _uiState.value.cards[index1]
            val card2 = _uiState.value.cards[index2]
            
            val isMatch = card1.content == card2.content
            
            val updatedCards = _uiState.value.cards.toMutableList()
            
            if (isMatch) {
                updatedCards[index1] = card1.match()
                updatedCards[index2] = card2.match()
                
                val newMatchedPairs = _uiState.value.matchedPairs + 1
                val totalPairs = _uiState.value.cards.size / 2
                
                // Calculate score based on difficulty and time
                val baseScore = when (_uiState.value.difficulty) {
                    Difficulty.EASY -> 10
                    Difficulty.MEDIUM -> 20
                    Difficulty.HARD -> 30
                }
                
                val timeBonus = ((60000 - _uiState.value.timeElapsed) / 1000).coerceAtLeast(0)
                val moveBonus = (100 - _uiState.value.moves).coerceAtLeast(0)
                val newScore = _uiState.value.score + baseScore + (timeBonus / 10) + (moveBonus / 10)
                
                val isCompleted = newMatchedPairs >= totalPairs
                
                _uiState.value = _uiState.value.copy(
                    cards = updatedCards,
                    flippedCards = emptyList(),
                    matchedPairs = newMatchedPairs,
                    score = newScore.toInt(),
                    isCompleted = isCompleted
                )
                
                if (isCompleted) {
                    finishGame()
                }
            } else {
                // Flip back
                updatedCards[index1] = card1.reset()
                updatedCards[index2] = card2.reset()
                
                _uiState.value = _uiState.value.copy(
                    cards = updatedCards,
                    flippedCards = emptyList()
                )
            }
        }
    }
    
    private fun finishGame() {
        timerJob?.cancel()
        val finalScore = _uiState.value.score
        
        viewModelScope.launch {
            saveGameScoreUseCase(
                com.braindrive.core.domain.model.GameScore(
                    playerName = _uiState.value.playerName,
                    score = finalScore,
                    gameType = _uiState.value.gameType
                )
            )
        }
    }
    
    override fun onCleared() {
        super.onCleared()
        timerJob?.cancel()
    }
}

