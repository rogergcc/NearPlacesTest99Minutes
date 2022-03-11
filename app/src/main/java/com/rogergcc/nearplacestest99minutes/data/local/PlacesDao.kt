package com.rogergcc.nearplacestest99minutes.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.rogergcc.nearplacestest99minutes.data.model.PlaceEntity

@Dao
interface PlacesDao {

    @Query("SELECT * FROM placeEntity")
    suspend fun getAllFavoritesPlaces(): List<PlaceEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveFavoritePlace(placeEntity: PlaceEntity)

}