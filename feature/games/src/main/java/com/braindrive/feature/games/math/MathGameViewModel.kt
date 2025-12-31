package com.braindrive.feature.games.math

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.braindrive.core.domain.model.MathOperation
import com.braindrive.core.domain.model.MathQuestion
import com.braindrive.core.domain.usecase.GenerateMathQuestionUseCase
import com.braindrive.core.domain.usecase.SaveGameScoreUseCase
import com.braindrive.core.domain.usecase.GetUserPreferencesUseCase
import com.braindrive.core.domain.model.GameType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

data class MathGameUiState(
    val currentQuestion: MathQuestion? = null,
    val score: Int = 0,
    val timeRemaining: Long = 60000L, // 60 seconds
    val isGameActive: Boolean = false,
    val showFeedback: FeedbackType? = null,
    val gameCompleted: Boolean = false,
    val playerName: String = ""
)

enum class FeedbackType {
    CORRECT,
    INCORRECT
}

sealed class MathGameIntent {
    object StartGame : MathGameIntent()
    data class SelectOperation(val operation: MathOperation) : MathGameIntent()
    object DismissFeedback : MathGameIntent()
    object FinishGame : MathGameIntent()
}

@HiltViewModel
class MathGameViewModel @Inject constructor(
    private val generateMathQuestionUseCase: GenerateMathQuestionUseCase,
    private val saveGameScoreUseCase: SaveGameScoreUseCase,
    private val getUserPreferencesUseCase: GetUserPreferencesUseCase
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(MathGameUiState())
    val uiState: StateFlow<MathGameUiState> = _uiState.asStateFlow()
    
    private var timerJob: Job? = null
    
    fun handleIntent(intent: MathGameIntent) {
        when (intent) {
            is MathGameIntent.StartGame -> startGame()
            is MathGameIntent.SelectOperation -> checkAnswer(intent.operation)
            is MathGameIntent.DismissFeedback -> dismissFeedback()
            is MathGameIntent.FinishGame -> finishGame()
        }
    }
    
    private fun startGame() {
        viewModelScope.launch {
            val preferences = getUserPreferencesUseCase().first()
            _uiState.value = _uiState.value.copy(
                isGameActive = true,
                gameCompleted = false,
                score = 0,
                timeRemaining = 60000L,
                playerName = preferences.userName.ifEmpty { "Player" }
            )
            generateNewQuestion()
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
    
    private fun generateNewQuestion() {
        val question = generateMathQuestionUseCase()
        _uiState.value = _uiState.value.copy(
            currentQuestion = question,
            showFeedback = null
        )
    }
    
    private fun checkAnswer(selectedOperation: MathOperation) {
        val currentQuestion = _uiState.value.currentQuestion ?: return
        
        val isCorrect = currentQuestion.operation == selectedOperation
        
        _uiState.value = _uiState.value.copy(
            score = if (isCorrect) _uiState.value.score + 1 else _uiState.value.score - 1,
            showFeedback = if (isCorrect) FeedbackType.CORRECT else FeedbackType.INCORRECT
        )
        
        viewModelScope.launch {
            delay(500)
            generateNewQuestion()
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
                    gameType = GameType.MATH
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

