package com.rogergcc.nearplacestest99minutes.ui.permission

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.rogergcc.nearplacestest99minutes.R
import com.rogergcc.nearplacestest99minutes.databinding.FragmentPermissionBinding
import com.rogergcc.nearplacestest99minutes.ui.main.PlacesFragmentDirections
import com.rogergcc.nearplacestest99minutes.ui.utils.Permissions
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [PermissionFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PermissionFragment : Fragment(), EasyPermissions.PermissionCallbacks {

    private var _binding: FragmentPermissionBinding? = null
    private val binding get() = _binding!!

//    private val sharedViewModel: SharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentPermissionBinding.inflate(inflater, container, false)

        binding.continueButton.setOnClickListener {
            if (Permissions.hasLocationPermission(requireContext())) {
//                checkFirstLaunch()

//                val action = PermissionFragmentDirections()

                findNavController().navigate(R.id.action_permissionFragment_to_movieFragment)
            } else {
                Permissions.requestsLocationPermission(this)
            }
        }

        return binding.root
    }

    private fun checkFirstLaunch() {
//        sharedViewModel.readFirstLaunch.observeOnce(viewLifecycleOwner, { firstLaunch ->
//            if (firstLaunch) {
//                findNavController().navigate(R.id.action_permissionFragment_to_add_geofence_graph)
//                sharedViewModel.saveFirstLaunch(false)
//            } else {
//                findNavController().navigate(R.id.action_permissionFragment_to_mapsFragment)
//            }
//        })
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    override fun onPermissionsDenied(requestCode: Int, perms: List<String>) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            AppSettingsDialog.Builder(requireActivity()).build().show()
        } else {
            Permissions.requestsLocationPermission(this)
        }
    }

    override fun onPermissionsGranted(requestCode: Int, perms: List<String>) {
        Toast.makeText(
            requireContext(),
            "Permission Granted! Tap on 'Continue' button to proceed.",
            Toast.LENGTH_SHORT
        ).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}