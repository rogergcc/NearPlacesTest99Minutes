package com.rogergcc.nearplacestest99minutes.data.remote

import com.rogergcc.nearplacestest99minutes.data.model.PlaceList
import com.rogergcc.nearplacestest99minutes.domain.WebService
import javax.inject.Inject

//@ExperimentalCoroutinesApi
class RemotePlacesDataSource @Inject constructor(private val webService: WebService) {


    suspend fun getNearPlacesDS(latitude: String, longitude: String): PlaceList {
        return webService.getNearPlaces(latitude, longitude)
    }

}