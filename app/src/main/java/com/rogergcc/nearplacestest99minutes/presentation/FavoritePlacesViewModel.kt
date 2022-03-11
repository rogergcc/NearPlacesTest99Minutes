package com.rogergcc.nearplacestest99minutes.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.rogergcc.nearplacestest99minutes.core.Resource
import com.rogergcc.nearplacestest99minutes.data.model.PlaceItem
import com.rogergcc.nearplacestest99minutes.domain.usecase.GetFavoritesPlacesUseCase
import com.rogergcc.nearplacestest99minutes.domain.usecase.GetPlacesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class FavoritePlacesViewModel @Inject constructor(
    private val repo: GetFavoritesPlacesUseCase
    )
    : ViewModel() {

    fun saveFavoritePlace(placeItem: PlaceItem) = liveData(Dispatchers.IO) {
        emit(Resource.Loading())
        try {
            emit(Resource.Success((repo.invoke(placeItem))))
        } catch (e: Exception) {
            emit(Resource.Failure(e))
        }
    }

}