package com.braindrive.feature.games.math

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.braindrive.core.domain.model.MathOperation
import com.braindrive.core.domain.model.MathQuestion
import com.braindrive.core.ui.theme.SuccessGreen
import com.braindrive.core.ui.theme.ErrorRed

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MathGameScreen(
    playerName: String,
    onNavigateBack: () -> Unit,
    onGameComplete: (Int) -> Unit,
    viewModel: MathGameViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    
    LaunchedEffect(Unit) {
        viewModel.handleIntent(MathGameIntent.StartGame)
    }
    
    LaunchedEffect(uiState.gameCompleted) {
        if (uiState.gameCompleted) {
            onGameComplete(uiState.score)
        }
    }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Math It") },
                navigationIcon = {
                    IconButton(onClick = {
                        viewModel.handleIntent(MathGameIntent.FinishGame)
                        onNavigateBack()
                    }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(padding)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                // Score and Timer
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    ScoreCard("Score", uiState.score.toString())
                    TimerCard(uiState.timeRemaining)
                }
                
                Spacer(modifier = Modifier.height(32.dp))
                
                // Question
                uiState.currentQuestion?.let { question ->
                    QuestionCard(question)
                }
                
                Spacer(modifier = Modifier.height(32.dp))
                
                // Operation Buttons
                OperationButtons(
                    onOperationSelected = { operation ->
                        viewModel.handleIntent(MathGameIntent.SelectOperation(operation))
                    }
                )
            }
            
            // Feedback Overlay
            uiState.showFeedback?.let { feedback ->
                FeedbackOverlay(feedback)
            }
        }
    }
}

@Composable
fun ScoreCard(label: String, value: String) {
    Card(
        modifier = Modifier.width(120.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = label,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSecondaryContainer
            )
            Text(
                text = value,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSecondaryContainer
            )
        }
    }
}

@Composable
fun TimerCard(timeRemaining: Long) {
    val seconds = (timeRemaining / 1000).toInt()
    val minutes = seconds / 60
    val remainingSeconds = seconds % 60
    
    Card(
        modifier = Modifier.width(120.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.tertiaryContainer
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Time",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onTertiaryContainer
            )
            Text(
                text = String.format("%02d:%02d", minutes, remainingSeconds),
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onTertiaryContainer
            )
        }
    }
}

@Composable
fun QuestionCard(question: MathQuestion) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "${question.number1} ? ${question.number2} = ${question.result.toInt()}",
                style = MaterialTheme.typography.displayMedium,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Select the correct operation",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.7f),
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun OperationButtons(
    onOperationSelected: (MathOperation) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        OperationButton(
            operation = MathOperation.ADDITION,
            symbol = "+",
            onClick = { onOperationSelected(MathOperation.ADDITION) },
            modifier = Modifier.weight(1f)
        )
        OperationButton(
            operation = MathOperation.SUBTRACTION,
            symbol = "-",
            onClick = { onOperationSelected(MathOperation.SUBTRACTION) },
            modifier = Modifier.weight(1f)
        )
        OperationButton(
            operation = MathOperation.MULTIPLICATION,
            symbol = "×",
            onClick = { onOperationSelected(MathOperation.MULTIPLICATION) },
            modifier = Modifier.weight(1f)
        )
        OperationButton(
            operation = MathOperation.DIVISION,
            symbol = "÷",
            onClick = { onOperationSelected(MathOperation.DIVISION) },
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
fun OperationButton(
    operation: MathOperation,
    symbol: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition(label = "button_pulse")
    val scale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "scale"
    )
    
    Button(
        onClick = onClick,
        modifier = modifier
            .height(80.dp)
            .scale(scale),
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary
        )
    ) {
        Text(
            text = symbol,
            style = MaterialTheme.typography.displaySmall,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onPrimary
        )
    }
}

@Composable
fun FeedbackOverlay(feedback: FeedbackType) {
    val color = when (feedback) {
        FeedbackType.CORRECT -> SuccessGreen
        FeedbackType.INCORRECT -> ErrorRed
    }
    
    val text = when (feedback) {
        FeedbackType.CORRECT -> "✓ Correct!"
        FeedbackType.INCORRECT -> "✗ Incorrect"
    }
    
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.3f)),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier
                .width(200.dp)
                .height(200.dp),
            shape = CircleShape,
            colors = CardDefaults.cardColors(
                containerColor = color
            )
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = text,
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        }
    }
}

