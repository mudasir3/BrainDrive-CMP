package com.braindrive.core.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.braindrive.core.domain.model.GameType

@Entity(tableName = "game_scores")
data class GameScoreEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val playerName: String,
    val score: Int,
    val gameType: GameType,
    val timestamp: Long
)

