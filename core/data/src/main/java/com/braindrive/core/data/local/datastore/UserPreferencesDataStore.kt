package com.braindrive.core.data.local.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.braindrive.core.domain.model.UserPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_preferences")

@Singleton
class UserPreferencesDataStore @Inject constructor(
    private val context: Context
) {
    private val dataStore: DataStore<Preferences> = context.dataStore
    
    private object Keys {
        val USER_NAME = stringPreferencesKey("user_name")
        val SOUND_ENABLED = booleanPreferencesKey("sound_enabled")
        val VIBRATION_ENABLED = booleanPreferencesKey("vibration_enabled")
    }
    
    val userPreferences: Flow<UserPreferences> = dataStore.data.map { preferences ->
        UserPreferences(
            userName = preferences[Keys.USER_NAME] ?: "",
            soundEnabled = preferences[Keys.SOUND_ENABLED] ?: true,
            vibrationEnabled = preferences[Keys.VIBRATION_ENABLED] ?: true
        )
    }
    
    suspend fun updateUserName(name: String) {
        dataStore.edit { preferences ->
            preferences[Keys.USER_NAME] = name
        }
    }
    
    suspend fun updateSoundEnabled(enabled: Boolean) {
        dataStore.edit { preferences ->
            preferences[Keys.SOUND_ENABLED] = enabled
        }
    }
    
    suspend fun updateVibrationEnabled(enabled: Boolean) {
        dataStore.edit { preferences ->
            preferences[Keys.VIBRATION_ENABLED] = enabled
        }
    }
    
    suspend fun getUserPreferences(): UserPreferences {
        return userPreferences.first()
    }
}

