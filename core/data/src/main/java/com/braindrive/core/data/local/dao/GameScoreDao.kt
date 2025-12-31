package com.braindrive.core.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.braindrive.core.data.local.entity.GameScoreEntity
import com.braindrive.core.domain.model.GameType
import kotlinx.coroutines.flow.Flow

@Dao
interface GameScoreDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertScore(score: GameScoreEntity)
    
    @Query("SELECT * FROM game_scores WHERE gameType = :gameType ORDER BY score DESC, timestamp DESC")
    fun getScoresByGameType(gameType: GameType): Flow<List<GameScoreEntity>>
    
    @Query("SELECT * FROM game_scores ORDER BY score DESC, timestamp DESC")
    fun getAllScores(): Flow<List<GameScoreEntity>>
    
    @Query("SELECT * FROM game_scores WHERE gameType = :gameType ORDER BY score DESC LIMIT 1")
    suspend fun getHighScore(gameType: GameType): GameScoreEntity?
    
    @Query("SELECT * FROM game_scores ORDER BY score DESC, timestamp DESC")
    suspend fun getAllScoresList(): List<GameScoreEntity>
}

