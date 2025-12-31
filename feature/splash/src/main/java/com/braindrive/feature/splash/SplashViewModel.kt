package com.braindrive.feature.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.braindrive.core.domain.usecase.GetUserPreferencesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class SplashUiState(
    val isLoading: Boolean = true,
    val userName: String = ""
)

sealed class SplashIntent {
    object LoadData : SplashIntent()
}

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val getUserPreferencesUseCase: GetUserPreferencesUseCase
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(SplashUiState())
    val uiState: StateFlow<SplashUiState> = _uiState.asStateFlow()
    
    init {
        handleIntent(SplashIntent.LoadData)
    }
    
    fun handleIntent(intent: SplashIntent) {
        when (intent) {
            is SplashIntent.LoadData -> loadData()
        }
    }
    
    private fun loadData() {
        viewModelScope.launch {
            getUserPreferencesUseCase().collect { preferences ->
                _uiState.value = _uiState.value.copy(
                    userName = preferences.userName,
                    isLoading = false
                )
            }
        }
    }
}

