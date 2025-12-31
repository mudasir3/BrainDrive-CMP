package com.braindrive.core.data.repository

import com.braindrive.core.data.local.dao.GameScoreDao
import com.braindrive.core.data.local.entity.GameScoreEntity
import com.braindrive.core.data.mapper.toDomain
import com.braindrive.core.data.mapper.toEntity
import com.braindrive.core.domain.model.GameScore
import com.braindrive.core.domain.model.GameType
import com.braindrive.core.domain.repository.GameRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GameRepositoryImpl @Inject constructor(
    private val gameScoreDao: GameScoreDao
) : GameRepository {
    
    override suspend fun saveScore(score: GameScore) {
        gameScoreDao.insertScore(score.toEntity())
    }
    
    override fun getScores(gameType: GameType?): Flow<List<GameScore>> {
        return if (gameType != null) {
            gameScoreDao.getScoresByGameType(gameType).map { entities ->
                entities.map { it.toDomain() }
            }
        } else {
            gameScoreDao.getAllScores().map { entities ->
                entities.map { it.toDomain() }
            }
        }
    }
    
    override suspend fun getHighScore(gameType: GameType): GameScore? {
        return gameScoreDao.getHighScore(gameType)?.toDomain()
    }
    
    override suspend fun getAllScores(): List<GameScore> {
        return gameScoreDao.getAllScoresList().map { it.toDomain() }
    }
}

