package com.rogergcc.nearplacestest99minutes.domain.usecase


import com.rogergcc.nearplacestest99minutes.data.model.PlaceItem
import com.rogergcc.nearplacestest99minutes.domain.PlaceRepository
import javax.inject.Inject

class GetFavoritesPlacesUseCase @Inject constructor(private val loginRepository: PlaceRepository) {
//    suspend fun invoke(placeItem :PlaceItem): Boolean {
//        return loginRepository.saveFavoritePlace(placeItem )
//    }

    suspend fun invoke(placeItem :PlaceItem): Boolean {
        return loginRepository.saveFavoritePlace(placeItem )
    }

}