package com.braindrive.core.domain.usecase

import com.braindrive.core.domain.repository.UserPreferencesRepository
import javax.inject.Inject

class UpdateUserNameUseCase @Inject constructor(
    private val repository: UserPreferencesRepository
) {
    suspend operator fun invoke(name: String) {
        repository.updateUserName(name)
    }
}

