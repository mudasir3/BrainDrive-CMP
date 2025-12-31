package com.braindrive.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.braindrive.core.domain.usecase.GetUserPreferencesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class HomeUiState(
    val userName: String = "",
    val isLoading: Boolean = true
)

sealed class HomeIntent {
    object LoadUserData : HomeIntent()
    object NavigateToGames : HomeIntent()
    object NavigateToSettings : HomeIntent()
}

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getUserPreferencesUseCase: GetUserPreferencesUseCase
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()
    
    init {
        handleIntent(HomeIntent.LoadUserData)
    }
    
    fun handleIntent(intent: HomeIntent) {
        when (intent) {
            is HomeIntent.LoadUserData -> loadUserData()
            is HomeIntent.NavigateToGames -> {}
            is HomeIntent.NavigateToSettings -> {}
        }
    }
    
    private fun loadUserData() {
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

