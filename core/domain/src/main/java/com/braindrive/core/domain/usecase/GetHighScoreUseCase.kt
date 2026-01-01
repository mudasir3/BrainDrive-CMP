package com.braindrive.core.domain.usecase

import com.braindrive.core.domain.model.GameScore
import com.braindrive.core.domain.model.GameType
import com.braindrive.core.domain.repository.GameRepository
import javax.inject.Inject

class GetHighScoreUseCase @Inject constructor(
    private val repository: GameRepository
) {
    suspend operator fun invoke(gameType: GameType): GameScore? {
        return repository.getHighScore(gameType)
    }
}

