package com.braindrive.core.data.di

import android.content.Context
import androidx.room.Room
import com.braindrive.core.data.local.BrainDriveDatabase
import com.braindrive.core.data.local.dao.GameScoreDao
import com.braindrive.core.data.repository.GameRepositoryImpl
import com.braindrive.core.data.repository.UserPreferencesRepositoryImpl
import com.braindrive.core.data.local.datastore.UserPreferencesDataStore
import com.braindrive.core.domain.repository.GameRepository as DomainGameRepository
import com.braindrive.core.domain.repository.UserPreferencesRepository as DomainUserPreferencesRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {
    
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): BrainDriveDatabase {
        return Room.databaseBuilder(
            context,
            BrainDriveDatabase::class.java,
            "braindrive_database"
        ).build()
    }
    
    @Provides
    fun provideGameScoreDao(database: BrainDriveDatabase): GameScoreDao {
        return database.gameScoreDao()
    }
    
    @Provides
    @Singleton
    fun provideUserPreferencesDataStore(
        @ApplicationContext context: Context
    ): UserPreferencesDataStore {
        return UserPreferencesDataStore(context)
    }
}

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun bindGameRepository(
        gameRepositoryImpl: GameRepositoryImpl
    ): DomainGameRepository
    
    @Binds
    abstract fun bindUserPreferencesRepository(
        userPreferencesRepositoryImpl: UserPreferencesRepositoryImpl
    ): DomainUserPreferencesRepository
}

