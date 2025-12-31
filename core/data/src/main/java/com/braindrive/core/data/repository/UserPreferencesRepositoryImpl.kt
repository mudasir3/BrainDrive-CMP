package com.braindrive.core.data.repository

import com.braindrive.core.data.local.datastore.UserPreferencesDataStore
import com.braindrive.core.domain.model.UserPreferences
import com.braindrive.core.domain.repository.UserPreferencesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserPreferencesRepositoryImpl @Inject constructor(
    private val dataStore: UserPreferencesDataStore
) : UserPreferencesRepository {
    
    override val userPreferences: Flow<UserPreferences> = dataStore.userPreferences
    
    override suspend fun updateUserName(name: String) {
        dataStore.updateUserName(name)
    }
    
    override suspend fun updateSoundEnabled(enabled: Boolean) {
        dataStore.updateSoundEnabled(enabled)
    }
    
    override suspend fun updateVibrationEnabled(enabled: Boolean) {
        dataStore.updateVibrationEnabled(enabled)
    }
    
    override suspend fun getUserPreferences(): UserPreferences {
        return dataStore.getUserPreferences()
    }
}

