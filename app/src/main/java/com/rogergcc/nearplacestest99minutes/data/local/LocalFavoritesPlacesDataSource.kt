package com.rogergcc.nearplacestest99minutes.data.local

import com.rogergcc.nearplacestest99minutes.data.model.PlaceEntity
import com.rogergcc.nearplacestest99minutes.data.model.PlaceList
import com.rogergcc.nearplacestest99minutes.data.model.toPlaceList
import java.lang.Exception
import javax.inject.Inject

//@ExperimentalCoroutinesApi
class LocalFavoritesPlacesDataSource @Inject constructor(private val placesDao: PlacesDao) {


    suspend fun getFavoritesPlaces(): PlaceList {
        return placesDao.getAllFavoritesPlaces().toPlaceList()
    }

    suspend fun saveFavoritePlace(placeEntity: PlaceEntity):Boolean {
//        placesDao.saveFavoritePlace(placeEntity) != 0
        return try {
            placesDao.saveFavoritePlace(placeEntity)
            true
        }catch (ex: Exception){
            false
        }


//        if (placesDao.saveFavoritePlace(placeEntity)==0){
//            return false
//        }else
//            return true
    }
}