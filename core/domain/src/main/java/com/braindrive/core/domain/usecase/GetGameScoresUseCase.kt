package com.braindrive.core.domain.usecase

import com.braindrive.core.domain.model.GameScore
import com.braindrive.core.domain.model.GameType
import com.braindrive.core.domain.repository.GameRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetGameScoresUseCase @Inject constructor(
    private val repository: GameRepository
) {
    operator fun invoke(gameType: GameType? = null): Flow<List<GameScore>> {
        return repository.getScores(gameType)
    }
}

