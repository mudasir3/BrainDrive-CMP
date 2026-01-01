package com.braindrive.feature.games

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
import androidx.compose.ui.draw.scale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.braindrive.core.domain.model.Difficulty
import com.braindrive.core.domain.model.GameType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DifficultySelectionScreen(
    gameType: GameType,
    gameTitle: String,
    onNavigateBack: () -> Unit,
    onDifficultySelected: (Difficulty) -> Unit
) {
    var selectedDifficulty by remember { mutableStateOf<Difficulty?>(null) }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(gameTitle) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
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
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Select Difficulty",
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.padding(bottom = 48.dp)
                )
                
                Spacer(modifier = Modifier.height(32.dp))
                
                DifficultyButton(
                    difficulty = Difficulty.EASY,
                    description = "Perfect for beginners",
                    onClick = { onDifficultySelected(Difficulty.EASY) },
                    modifier = Modifier.fillMaxWidth()
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                DifficultyButton(
                    difficulty = Difficulty.MEDIUM,
                    description = "Balanced challenge",
                    onClick = { onDifficultySelected(Difficulty.MEDIUM) },
                    modifier = Modifier.fillMaxWidth()
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                DifficultyButton(
                    difficulty = Difficulty.HARD,
                    description = "For experts only",
                    onClick = { onDifficultySelected(Difficulty.HARD) },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@Composable
fun DifficultyButton(
    difficulty: Difficulty,
    description: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition(label = "button_pulse")
    val scale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.02f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "scale"
    )
    
    Card(
        modifier = modifier
            .height(100.dp)
            .clickable(onClick = onClick)
            .scale(scale),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = when (difficulty) {
                Difficulty.EASY -> MaterialTheme.colorScheme.primaryContainer
                Difficulty.MEDIUM -> MaterialTheme.colorScheme.secondaryContainer
                Difficulty.HARD -> MaterialTheme.colorScheme.tertiaryContainer
            }
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = difficulty.displayName,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = when (difficulty) {
                    Difficulty.EASY -> MaterialTheme.colorScheme.onPrimaryContainer
                    Difficulty.MEDIUM -> MaterialTheme.colorScheme.onSecondaryContainer
                    Difficulty.HARD -> MaterialTheme.colorScheme.onTertiaryContainer
                }
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = description,
                style = MaterialTheme.typography.bodyMedium,
                color = when (difficulty) {
                    Difficulty.EASY -> MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.7f)
                    Difficulty.MEDIUM -> MaterialTheme.colorScheme.onSecondaryContainer.copy(alpha = 0.7f)
                    Difficulty.HARD -> MaterialTheme.colorScheme.onTertiaryContainer.copy(alpha = 0.7f)
                },
                textAlign = TextAlign.Center
            )
        }
    }
}

