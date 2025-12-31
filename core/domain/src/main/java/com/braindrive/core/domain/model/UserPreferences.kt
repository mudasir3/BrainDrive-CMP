package com.braindrive.core.domain.model

data class UserPreferences(
    val userName: String = "",
    val soundEnabled: Boolean = true,
    val vibrationEnabled: Boolean = true
)

