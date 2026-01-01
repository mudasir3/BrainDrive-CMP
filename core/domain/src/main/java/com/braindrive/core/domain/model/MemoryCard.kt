package com.braindrive.core.domain.model

data class MemoryCard(
    val id: Int,
    val content: String, // Can be emoji, number, or text
    val isFlipped: Boolean = false,
    val isMatched: Boolean = false
) {
    fun flip(): MemoryCard = copy(isFlipped = !isFlipped)
    fun match(): MemoryCard = copy(isMatched = true, isFlipped = true)
    fun reset(): MemoryCard = copy(isFlipped = false, isMatched = false)
}

data class MemoryGameState(
    val cards: List<MemoryCard>,
    val flippedCards: List<Int> = emptyList(),
    val matchedPairs: Int = 0,
    val moves: Int = 0,
    val timeElapsed: Long = 0L,
    val isCompleted: Boolean = false
)

