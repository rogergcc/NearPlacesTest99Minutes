package com.rogergcc.nearplacestest99minutes.ui.main

import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.rogergcc.nearplacestest99minutes.R
import com.rogergcc.nearplacestest99minutes.core.Resource
import com.rogergcc.nearplacestest99minutes.data.model.PlaceItem
import com.rogergcc.nearplacestest99minutes.databinding.FragmentPlacesBinding
import com.rogergcc.nearplacestest99minutes.presentation.PlacesViewModel
import com.rogergcc.nearplacestest99minutes.ui.main.adapters.PlaceAdapter
import com.rogergcc.nearplacestest99minutes.ui.utils.GPSUtility
import com.rogergcc.nearplacestest99minutes.ui.utils.toast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PlacesFragment : Fragment(R.layout.fragment_places)
{

    private lateinit var binding: FragmentPlacesBinding

    private val viewModel: PlacesViewModel by viewModels()
    private val mAdapterPlacesList by lazy {
        PlaceAdapter() { placeItem ->
            goToPlaceDetailsView(placeItem)
        }
    }

    private var location: Location? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPlacesBinding.bind(view)

        val gps = GPSUtility(this@PlacesFragment.requireContext())
        location = gps.promptForGPS()
        if (location == null) {
//            TimberAppLogger.e("Gps should be activated")
            requireContext().toast("required GPS active")
            return
        }

        binding.rvPlaces.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = mAdapterPlacesList
        }


        val latitude = location?.latitude.toString()
        val longitude = location?.longitude.toString()

        viewModel.fetchHomeNearPlaces(latitude = latitude, longitude = longitude)
            .observe(viewLifecycleOwner, { result ->
                when (result) {
                    is Resource.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }
                    is Resource.Success -> {
                        binding.progressBar.visibility = View.GONE

                        mAdapterPlacesList.mItemsPlace = result.data.results
                    }
                    is Resource.Failure -> {
                        binding.progressBar.visibility = View.GONE
                        Log.e("FetchError", "Error: ${result.exception} ")
                        Toast.makeText(
                            requireContext(),
                            "Error: ${result.exception}",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    }
                }
            })
    }

    private fun goToPlaceDetailsView(placeItem: PlaceItem) {
        Log.d(TAG, "place  $placeItem")
//        requireContext().toast(prevention.toString())


        val action = PlacesFragmentDirections.actionPlacesFragmentToPlacesDetailFragment(
            placeItem.photo.toString(),
            placeItem.name.toString(),
            placeItem.name.toString(),
            placeItem.vicinity.toString(),
            placeItem

        )
        findNavController().navigate(action)
    }


    companion object {
        private const val TAG = "PlacesFragment"
    }
}
