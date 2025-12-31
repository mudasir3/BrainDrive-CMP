package com.braindrive.core.domain.usecase

import com.braindrive.core.domain.model.GameScore
import com.braindrive.core.domain.repository.GameRepository
import javax.inject.Inject

class SaveGameScoreUseCase @Inject constructor(
    private val repository: GameRepository
) {
    suspend operator fun invoke(score: GameScore) {
        repository.saveScore(score)
    }
}

