package com.braindrive.core.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.braindrive.core.data.local.dao.GameScoreDao
import com.braindrive.core.data.local.entity.GameScoreEntity
import com.braindrive.core.data.local.converter.GameTypeConverter

@Database(
    entities = [GameScoreEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(GameTypeConverter::class)
abstract class BrainDriveDatabase : RoomDatabase() {
    abstract fun gameScoreDao(): GameScoreDao
}

