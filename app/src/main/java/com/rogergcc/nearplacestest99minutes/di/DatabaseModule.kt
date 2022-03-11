package com.rogergcc.nearplacestest99minutes.di

import android.content.Context
import androidx.room.Room
import com.rogergcc.nearplacestest99minutes.application.AppConstants
import com.rogergcc.nearplacestest99minutes.data.local.AppDatabase
import com.rogergcc.nearplacestest99minutes.data.local.PlacesDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            AppConstants.DB_NAME
        ).fallbackToDestructiveMigration()
            .build()
    }
    @Provides
    @Singleton
    fun providePlacesDao(placesDatabase: AppDatabase): PlacesDao {
        return placesDatabase.placesDao()
    }

}