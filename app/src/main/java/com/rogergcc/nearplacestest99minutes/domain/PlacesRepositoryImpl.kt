package com.rogergcc.nearplacestest99minutes.domain

import com.rogergcc.nearplacestest99minutes.core.InternetCheck
import com.rogergcc.nearplacestest99minutes.data.local.LocalFavoritesPlacesDataSource
import com.rogergcc.nearplacestest99minutes.data.model.PlaceItem
import com.rogergcc.nearplacestest99minutes.data.model.PlaceList
import com.rogergcc.nearplacestest99minutes.data.model.toPlaceEntity
import com.rogergcc.nearplacestest99minutes.data.remote.RemotePlacesDataSource

//@ExperimentalCoroutinesApi
//@ActivityRetainedScoped
class PlacesRepositoryImpl constructor(
    private val dataSourceRemote: RemotePlacesDataSource,
    private val dataSourceLocal: LocalFavoritesPlacesDataSource
) : PlaceRepository {


    override suspend fun getNearPlaces(latitude: String, longitude: String): PlaceList {
        return if (InternetCheck.isNetworkAvailable()) {
            dataSourceRemote.getNearPlacesDS(latitude, longitude)
        } else dataSourceLocal.getFavoritesPlaces()

    }

    override suspend fun saveFavoritePlace(placeItem: PlaceItem): Boolean {
        return try {
            dataSourceLocal.saveFavoritePlace(placeItem.toPlaceEntity())
        } catch (ex: Exception) {
            false
        }
    }
}