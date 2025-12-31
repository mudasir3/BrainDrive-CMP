package com.braindrive.core.data.local.converter

import androidx.room.TypeConverter
import com.braindrive.core.domain.model.GameType

class GameTypeConverter {
    @TypeConverter
    fun fromGameType(gameType: GameType): String {
        return gameType.name
    }
    
    @TypeConverter
    fun toGameType(gameTypeString: String): GameType {
        return GameType.valueOf(gameTypeString)
    }
}

