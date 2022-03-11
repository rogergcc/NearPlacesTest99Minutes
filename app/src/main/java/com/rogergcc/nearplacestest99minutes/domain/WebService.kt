package com.rogergcc.nearplacestest99minutes.domain

import com.rogergcc.nearplacestest99minutes.data.model.PlaceList
import retrofit2.http.GET
import retrofit2.http.Query

interface WebService {
    @GET("search-near-places")
    //latitude=-18.0117558&longitude=-70.2503458
    suspend fun getNearPlaces(
        @Query("latitude") latitude: String, @Query("longitude") longitude: String
    ): PlaceList
}
//http://192.168.0.15:3001/api/v1/search-near-places?latitude=-18.0117558&longitude=-70.2503458