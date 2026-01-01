package com.braindrive.core.domain.model

enum class Difficulty(val displayName: String, val multiplier: Float) {
    EASY("Easy", 1.0f),
    MEDIUM("Medium", 1.5f),
    HARD("Hard", 2.0f)
}

