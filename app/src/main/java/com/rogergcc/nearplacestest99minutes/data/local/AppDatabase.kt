package com.rogergcc.nearplacestest99minutes.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.rogergcc.nearplacestest99minutes.data.model.PlaceEntity

@Database(entities = [PlaceEntity::class], version = 1,exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun placesDao(): PlacesDao
}