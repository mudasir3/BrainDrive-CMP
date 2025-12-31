package com.braindrive.core.data.mapper

import com.braindrive.core.data.local.entity.GameScoreEntity
import com.braindrive.core.domain.model.GameScore

fun GameScore.toEntity(): GameScoreEntity {
    return GameScoreEntity(
        id = id,
        playerName = playerName,
        score = score,
        gameType = gameType,
        timestamp = timestamp
    )
}

fun GameScoreEntity.toDomain(): GameScore {
    return GameScore(
        id = id,
        playerName = playerName,
        score = score,
        gameType = gameType,
        timestamp = timestamp
    )
}

