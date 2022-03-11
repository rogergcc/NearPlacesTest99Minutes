package com.rogergcc.nearplacestest99minutes.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.rogergcc.nearplacestest99minutes.core.Resource
import com.rogergcc.nearplacestest99minutes.data.model.PlaceItem
import com.rogergcc.nearplacestest99minutes.domain.usecase.GetPlacesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class PlacesViewModel @Inject constructor(
    private val repo: GetPlacesUseCase
    )
    : ViewModel() {

    fun fetchHomeNearPlaces(latitude: String, longitude: String) = liveData(Dispatchers.IO) {
        emit(Resource.Loading())
        try {
            emit(Resource.Success((repo.invoke(latitude,longitude))))
        } catch (e: Exception) {
            emit(Resource.Failure(e))
        }
    }



}