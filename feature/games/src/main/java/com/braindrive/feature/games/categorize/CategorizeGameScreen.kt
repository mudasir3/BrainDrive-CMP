package com.braindrive.feature.games.categorize

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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
import com.braindrive.core.domain.model.ItemCategory
import com.braindrive.core.ui.theme.SuccessGreen
import com.braindrive.core.ui.theme.ErrorRed

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategorizeGameScreen(
    playerName: String,
    gameType: com.braindrive.core.domain.model.GameType,
    difficulty: com.braindrive.core.domain.model.Difficulty,
    onNavigateBack: () -> Unit,
    onGameComplete: (Int) -> Unit,
    viewModel: CategorizeGameViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    
    LaunchedEffect(Unit) {
        viewModel.handleIntent(CategorizeGameIntent.StartGame(gameType, difficulty))
    }
    
    LaunchedEffect(uiState.gameCompleted) {
        if (uiState.gameCompleted) {
            onGameComplete(uiState.score)
        }
    }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(getGameTitle(gameType)) },
                navigationIcon = {
                    IconButton(onClick = {
                        viewModel.handleIntent(CategorizeGameIntent.FinishGame)
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
                
                // Category Buttons
                uiState.currentQuestion?.let { question ->
                    CategoryButtons(
                        categories = question.options,
                        onCategorySelected = { category ->
                            viewModel.handleIntent(CategorizeGameIntent.SelectCategory(category))
                        }
                    )
                }
            }
            
            // Feedback Overlay
            uiState.showFeedback?.let { feedback ->
                FeedbackOverlay(feedback)
            }
        }
    }
}

fun getGameTitle(gameType: com.braindrive.core.domain.model.GameType): String {
    return when (gameType) {
        com.braindrive.core.domain.model.GameType.CATEGORIZE_EDIBLE -> "Categorize - Edible"
        com.braindrive.core.domain.model.GameType.CATEGORIZE_CONSUMER -> "Categorize - Consumer"
        com.braindrive.core.domain.model.GameType.CATEGORIZE_HUMAN -> "Categorize - Human"
        else -> "Categorize"
    }
}

@Composable
fun QuestionCard(question: com.braindrive.core.domain.model.CategorizeQuestion) {
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
                text = question.item.name,
                style = MaterialTheme.typography.displayMedium,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Select the correct category",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.7f),
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun CategoryButtons(
    categories: List<ItemCategory>,
    onCategorySelected: (ItemCategory) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        categories.forEach { category ->
            CategoryButton(
                category = category,
                onClick = { onCategorySelected(category) },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
fun CategoryButton(
    category: ItemCategory,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition(label = "button_pulse")
    val scale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.05f,
        animationSpec = infiniteRepeatable(
            animation = tween(1500, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "scale"
    )
    
    Button(
        onClick = onClick,
        modifier = modifier
            .height(70.dp)
            .scale(scale),
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary
        )
    ) {
        Text(
            text = category.name,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onPrimary
        )
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
fun FeedbackOverlay(feedback: com.braindrive.feature.games.categorize.FeedbackType) {
    val color = when (feedback) {
        com.braindrive.feature.games.categorize.FeedbackType.CORRECT -> SuccessGreen
        com.braindrive.feature.games.categorize.FeedbackType.INCORRECT -> ErrorRed
    }
    
    val text = when (feedback) {
        com.braindrive.feature.games.categorize.FeedbackType.CORRECT -> "✓ Correct!"
        com.braindrive.feature.games.categorize.FeedbackType.INCORRECT -> "✗ Incorrect"
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
            shape = androidx.compose.foundation.shape.CircleShape,
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

