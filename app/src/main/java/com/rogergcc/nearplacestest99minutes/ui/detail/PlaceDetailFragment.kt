package com.rogergcc.nearplacestest99minutes.ui.detail

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.rogergcc.nearplacestest99minutes.R
import com.rogergcc.nearplacestest99minutes.core.Resource
import com.rogergcc.nearplacestest99minutes.databinding.FragmentPlaceDetailBinding
import com.rogergcc.nearplacestest99minutes.presentation.FavoritePlacesViewModel
import com.rogergcc.nearplacestest99minutes.ui.utils.toast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PlaceDetailFragment : Fragment(R.layout.fragment_place_detail) {

    private val viewModel: FavoritePlacesViewModel by viewModels()

    private val args by navArgs<PlaceDetailFragmentArgs>()
    private lateinit var binding: FragmentPlaceDetailBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPlaceDetailBinding.bind(view)
        Glide.with(requireContext()).load(args.backgroundImageUrl).centerCrop()
            .into(binding.imgPlace)
        Glide.with(requireContext()).load(args.backgroundImageUrl).centerCrop()
            .into(binding.imgBackground)
        binding.txtDescription.text = args.releaseDate
        binding.txtPlaceTitle.text = args.title
        binding.txtLanguage.text = "${args.overview}"
//        binding.txtRating.text = "${args.voteAverage} (${args.voteCount} Reviews)"
//        binding.txtReleased.text = "${args.releaseDate}"

        binding.linearLayout2.setOnClickListener {

            viewModel.saveFavoritePlace(args.placeItemArg!!)
                .observe(viewLifecycleOwner, { result ->
                    when (result) {
                        is Resource.Loading -> {
//                            binding.progressBar.visibility = View.VISIBLE
                        }
                        is Resource.Success -> {
//                            binding.progressBar.visibility = View.GONE
                            if (result.data)
                                requireContext().toast("Place add to Favorites")
                            else
                                requireContext().toast("Place Not")

                        }
                        is Resource.Failure -> {
//                            binding.progressBar.visibility = View.GONE
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
    }
}