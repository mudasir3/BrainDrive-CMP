package com.braindrive.core.domain.repository

import com.braindrive.core.domain.model.GameScore
import com.braindrive.core.domain.model.GameType
import kotlinx.coroutines.flow.Flow

interface GameRepository {
    suspend fun saveScore(score: GameScore)
    fun getScores(gameType: GameType? = null): Flow<List<GameScore>>
    suspend fun getHighScore(gameType: GameType): GameScore?
    suspend fun getAllScores(): List<GameScore>
}

