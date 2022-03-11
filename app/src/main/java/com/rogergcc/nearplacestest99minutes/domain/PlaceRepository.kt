package com.rogergcc.nearplacestest99minutes.domain


import com.rogergcc.nearplacestest99minutes.data.model.PlaceItem
import com.rogergcc.nearplacestest99minutes.data.model.PlaceList

interface PlaceRepository {

    suspend fun getNearPlaces(latitude: String, longitude: String): PlaceList
    suspend fun saveFavoritePlace(placeItem: PlaceItem): Boolean
}
