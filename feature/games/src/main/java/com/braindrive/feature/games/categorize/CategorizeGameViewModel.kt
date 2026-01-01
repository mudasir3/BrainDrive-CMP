package com.braindrive.feature.games.categorize

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.braindrive.core.domain.model.CategorizeQuestion
import com.braindrive.core.domain.model.Difficulty
import com.braindrive.core.domain.model.GameType
import com.braindrive.core.domain.model.ItemCategory
import com.braindrive.core.domain.usecase.GenerateCategorizeQuestionUseCase
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

data class CategorizeGameUiState(
    val currentQuestion: CategorizeQuestion? = null,
    val score: Int = 0,
    val timeRemaining: Long = 60000L,
    val isGameActive: Boolean = false,
    val showFeedback: FeedbackType? = null,
    val gameCompleted: Boolean = false,
    val playerName: String = "",
    val difficulty: Difficulty = Difficulty.MEDIUM,
    val gameType: GameType = GameType.CATEGORIZE_EDIBLE
)

enum class FeedbackType {
    CORRECT,
    INCORRECT
}

sealed class CategorizeGameIntent {
    data class StartGame(val gameType: GameType, val difficulty: Difficulty) : CategorizeGameIntent()
    data class SelectCategory(val category: ItemCategory) : CategorizeGameIntent()
    object DismissFeedback : CategorizeGameIntent()
    object FinishGame : CategorizeGameIntent()
}

@HiltViewModel
class CategorizeGameViewModel @Inject constructor(
    private val generateCategorizeQuestionUseCase: GenerateCategorizeQuestionUseCase,
    private val saveGameScoreUseCase: SaveGameScoreUseCase,
    private val getUserPreferencesUseCase: GetUserPreferencesUseCase
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(CategorizeGameUiState())
    val uiState: StateFlow<CategorizeGameUiState> = _uiState.asStateFlow()
    
    private var timerJob: Job? = null
    
    fun handleIntent(intent: CategorizeGameIntent) {
        when (intent) {
            is CategorizeGameIntent.StartGame -> startGame(intent.gameType, intent.difficulty)
            is CategorizeGameIntent.SelectCategory -> checkAnswer(intent.category)
            is CategorizeGameIntent.DismissFeedback -> dismissFeedback()
            is CategorizeGameIntent.FinishGame -> finishGame()
        }
    }
    
    private fun startGame(gameType: GameType, difficulty: Difficulty) {
        viewModelScope.launch {
            val preferences = getUserPreferencesUseCase().first()
            val category = when (gameType) {
                GameType.CATEGORIZE_EDIBLE -> ItemCategory.EDIBLE
                GameType.CATEGORIZE_CONSUMER -> ItemCategory.CONSUMER
                GameType.CATEGORIZE_HUMAN -> ItemCategory.HUMAN
                else -> ItemCategory.EDIBLE
            }
            
            val timeLimit = when (difficulty) {
                Difficulty.EASY -> 90000L
                Difficulty.MEDIUM -> 60000L
                Difficulty.HARD -> 45000L
            }
            
            _uiState.value = _uiState.value.copy(
                isGameActive = true,
                gameCompleted = false,
                score = 0,
                timeRemaining = timeLimit,
                playerName = preferences.userName.ifEmpty { "Player" },
                difficulty = difficulty,
                gameType = gameType
            )
            generateNewQuestion(category, difficulty)
            startTimer()
        }
    }
    
    private fun startTimer() {
        timerJob?.cancel()
        timerJob = viewModelScope.launch {
            while (_uiState.value.timeRemaining > 0 && _uiState.value.isGameActive) {
                delay(1000)
                _uiState.value = _uiState.value.copy(
                    timeRemaining = _uiState.value.timeRemaining - 1000
                )
            }
            if (_uiState.value.timeRemaining <= 0) {
                finishGame()
            }
        }
    }
    
    private fun generateNewQuestion(category: ItemCategory, difficulty: Difficulty) {
        val question = generateCategorizeQuestionUseCase(category, difficulty)
        _uiState.value = _uiState.value.copy(
            currentQuestion = question,
            showFeedback = null
        )
    }
    
    private fun checkAnswer(selectedCategory: ItemCategory) {
        val currentQuestion = _uiState.value.currentQuestion ?: return
        
        val isCorrect = currentQuestion.isCorrect(selectedCategory)
        val points = if (isCorrect) {
            when (_uiState.value.difficulty) {
                Difficulty.EASY -> 1
                Difficulty.MEDIUM -> 2
                Difficulty.HARD -> 3
            }
        } else {
            -1
        }
        
        _uiState.value = _uiState.value.copy(
            score = (_uiState.value.score + points).coerceAtLeast(0),
            showFeedback = if (isCorrect) FeedbackType.CORRECT else FeedbackType.INCORRECT
        )
        
        viewModelScope.launch {
            delay(800)
            val category = when (_uiState.value.gameType) {
                GameType.CATEGORIZE_EDIBLE -> ItemCategory.EDIBLE
                GameType.CATEGORIZE_CONSUMER -> ItemCategory.CONSUMER
                GameType.CATEGORIZE_HUMAN -> ItemCategory.HUMAN
                else -> ItemCategory.EDIBLE
            }
            generateNewQuestion(category, _uiState.value.difficulty)
        }
    }
    
    private fun dismissFeedback() {
        _uiState.value = _uiState.value.copy(showFeedback = null)
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
        
        _uiState.value = _uiState.value.copy(
            isGameActive = false,
            gameCompleted = true
        )
    }
    
    override fun onCleared() {
        super.onCleared()
        timerJob?.cancel()
    }
}

