package com.braindrive.core.domain.usecase

import com.braindrive.core.domain.model.Difficulty
import com.braindrive.core.domain.model.MemoryCard
import javax.inject.Inject

class GenerateMemoryGameUseCase @Inject constructor() {
    
    private val emojiPairs = listOf(
        "🍎", "🍌", "🍇", "🍊", "🍓", "🍑", "🍒", "🥝",
        "🍕", "🍔", "🍟", "🌭", "🍿", "🧂", "🥓", "🥨",
        "🚗", "🚕", "🚙", "🚌", "🚎", "🏎️", "🚓", "🚑",
        "🐶", "🐱", "🐭", "🐹", "🐰", "🦊", "🐻", "🐼",
        "⚽", "🏀", "🏈", "⚾", "🎾", "🏐", "🏉", "🎱",
        "🎮", "🕹️", "🎯", "🎲", "🃏", "🀄", "🎴", "🎨"
    )
    
    private val numberPairs = (1..50).map { it.toString() }
    
    operator fun invoke(difficulty: Difficulty, useNumbers: Boolean = false): List<MemoryCard> {
        val pairCount = when (difficulty) {
            Difficulty.EASY -> 6 // 12 cards, 3x4 grid
            Difficulty.MEDIUM -> 9 // 18 cards, 3x6 or 4x5 grid
            Difficulty.HARD -> 12 // 24 cards, 4x6 grid
        }
        
        val contentList = if (useNumbers) {
            numberPairs.shuffled().take(pairCount)
        } else {
            emojiPairs.shuffled().take(pairCount)
        }
        
        val cards = mutableListOf<MemoryCard>()
        var id = 0
        
        // Create pairs
        contentList.forEach { content ->
            cards.add(MemoryCard(id = id++, content = content))
            cards.add(MemoryCard(id = id++, content = content))
        }
        
        return cards.shuffled()
    }
}

