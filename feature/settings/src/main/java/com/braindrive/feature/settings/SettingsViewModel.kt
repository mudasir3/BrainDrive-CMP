package com.braindrive.feature.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.braindrive.core.domain.usecase.GetUserPreferencesUseCase
import com.braindrive.core.domain.usecase.UpdateUserNameUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class SettingsUiState(
    val userName: String = "",
    val soundEnabled: Boolean = true,
    val vibrationEnabled: Boolean = true,
    val isLoading: Boolean = true
)

sealed class SettingsIntent {
    object LoadSettings : SettingsIntent()
    data class UpdateUserName(val name: String) : SettingsIntent()
    data class UpdateSoundEnabled(val enabled: Boolean) : SettingsIntent()
    data class UpdateVibrationEnabled(val enabled: Boolean) : SettingsIntent()
}

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val getUserPreferencesUseCase: GetUserPreferencesUseCase,
    private val updateUserNameUseCase: UpdateUserNameUseCase
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(SettingsUiState())
    val uiState: StateFlow<SettingsUiState> = _uiState.asStateFlow()
    
    init {
        handleIntent(SettingsIntent.LoadSettings)
    }
    
    fun handleIntent(intent: SettingsIntent) {
        when (intent) {
            is SettingsIntent.LoadSettings -> loadSettings()
            is SettingsIntent.UpdateUserName -> updateUserName(intent.name)
            is SettingsIntent.UpdateSoundEnabled -> updateSoundEnabled(intent.enabled)
            is SettingsIntent.UpdateVibrationEnabled -> updateVibrationEnabled(intent.enabled)
        }
    }
    
    private fun loadSettings() {
        viewModelScope.launch {
            getUserPreferencesUseCase().collect { preferences ->
                _uiState.value = _uiState.value.copy(
                    userName = preferences.userName,
                    soundEnabled = preferences.soundEnabled,
                    vibrationEnabled = preferences.vibrationEnabled,
                    isLoading = false
                )
            }
        }
    }
    
    private fun updateUserName(name: String) {
        viewModelScope.launch {
            updateUserNameUseCase(name)
            _uiState.value = _uiState.value.copy(userName = name)
        }
    }
    
    private fun updateSoundEnabled(enabled: Boolean) {
        // TODO: Implement sound preference update
        _uiState.value = _uiState.value.copy(soundEnabled = enabled)
    }
    
    private fun updateVibrationEnabled(enabled: Boolean) {
        // TODO: Implement vibration preference update
        _uiState.value = _uiState.value.copy(vibrationEnabled = enabled)
    }
}

