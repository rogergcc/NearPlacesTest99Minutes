package com.rogergcc.nearplacestest99minutes.ui.utils

import android.Manifest
import android.content.Context
import android.content.DialogInterface
import android.os.Build
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.rogergcc.nearplacestest99minutes.ui.utils.MyObjectConstant.PERMISSION_BACKGROUND_LOCATION_REQUEST_CODE
import com.rogergcc.nearplacestest99minutes.ui.utils.MyObjectConstant.PERMISSION_LOCATION_REQUEST_CODE
import pub.devrel.easypermissions.EasyPermissions

/**
 * Created by rogergcc on July.
 * Copyright Ⓒ 2021 . All rights reserved.
 */

object Permissions {

    fun hasLocationPermission(context: Context) =
        EasyPermissions.hasPermissions(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        )

    fun requestsLocationPermission(fragment: Fragment) {
        EasyPermissions.requestPermissions(
            fragment,
            "This application cannot work without Location Permission.",
            PERMISSION_LOCATION_REQUEST_CODE,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
    }

    fun hasBackgroundLocationPermission(context: Context): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            return EasyPermissions.hasPermissions(
                context,
                Manifest.permission.ACCESS_BACKGROUND_LOCATION
            )
        }
        return true
    }

    fun requestsBackgroundLocationPermission(fragment: Fragment) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            EasyPermissions.requestPermissions(
                fragment,
                "Background location permission is essential to this application. Without it we will not be able to provide you with our service.",
                PERMISSION_BACKGROUND_LOCATION_REQUEST_CODE,
                Manifest.permission.ACCESS_BACKGROUND_LOCATION
            )
        }
    }

    fun showDialog(
        context: Context,
        title: String,
        content: String,
        positiveText: String,
        positiveAction: DialogInterface.OnClickListener,
        negativeText: String? = null,
//        negativeAction: DialogInterface.OnClickListener? = null,
        dismiss: Boolean = true
    ) {
        val dialogBuilder =
            AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(content)
                .setCancelable(dismiss)
                .setPositiveButton(positiveText, positiveAction)
//        dialogBuilder.setPositiveButton(
//            positiveText
//        ) { dialog, which ->
////            val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
////            mContext.startActivity(intent)
//        }
                .setNegativeButton(
                    negativeText
                ) { dialog, which -> dialog.cancel() }

        // Showing Alert Message
        dialogBuilder.show()

    }

}