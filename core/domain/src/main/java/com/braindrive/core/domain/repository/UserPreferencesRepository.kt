package com.braindrive.core.domain.repository

import com.braindrive.core.domain.model.UserPreferences
import kotlinx.coroutines.flow.Flow

interface UserPreferencesRepository {
    val userPreferences: Flow<UserPreferences>
    suspend fun updateUserName(name: String)
    suspend fun updateSoundEnabled(enabled: Boolean)
    suspend fun updateVibrationEnabled(enabled: Boolean)
    suspend fun getUserPreferences(): UserPreferences
}

