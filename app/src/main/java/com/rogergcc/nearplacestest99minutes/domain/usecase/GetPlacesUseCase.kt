package com.rogergcc.nearplacestest99minutes.domain.usecase


import com.rogergcc.nearplacestest99minutes.data.model.PlaceList
import com.rogergcc.nearplacestest99minutes.domain.PlaceRepository
import javax.inject.Inject

class GetPlacesUseCase @Inject constructor(private val loginRepository: PlaceRepository) {
    suspend fun invoke(latitude:String, longitude:String): PlaceList {
        return loginRepository.getNearPlaces(latitude, longitude)
    }


}