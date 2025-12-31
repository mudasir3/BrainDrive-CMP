package com.braindrive.core.domain.usecase

import com.braindrive.core.domain.model.UserPreferences
import com.braindrive.core.domain.repository.UserPreferencesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUserPreferencesUseCase @Inject constructor(
    private val repository: UserPreferencesRepository
) {
    operator fun invoke(): Flow<UserPreferences> = repository.userPreferences
}

