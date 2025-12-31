package com.braindrive.core.domain.model

data class GameScore(
    val id: Long = 0,
    val playerName: String,
    val score: Int,
    val gameType: GameType,
    val timestamp: Long = System.currentTimeMillis()
)

enum class GameType {
    MATH,
    CATEGORIZE_EDIBLE,
    CATEGORIZE_CONSUMER,
    CATEGORIZE_HUMAN,
    MEMORY_SECOND,
    MEMORY_THIRD
}

