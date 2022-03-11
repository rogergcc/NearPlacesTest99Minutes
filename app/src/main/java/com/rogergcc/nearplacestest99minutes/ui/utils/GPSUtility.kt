package com.rogergcc.nearplacestest99minutes.ui.utils

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Service
import android.content.Context
import android.content.Intent
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.os.IBinder
import android.provider.Settings
import android.util.Log
import com.rogergcc.nearplacestest99minutes.R


/**
 * Credit to http://www.androidhive.info/2012/07/android-gps-location-manager-tutorial/
 */
class GPSUtility(private val mContext: Context) : Service(),
    LocationListener {
    // Declaring a Location Manager
    protected var locationManager: LocationManager? = null

    // flag for GPS status
    var isGPSEnabled = false

    // flag for network status
    var isNetworkEnabled = false

    // flag for GPS status
    var canGetLocation = false
    private var location: Location? = null // location
    private var latitude // latitude
            = 0.0
    private var longitude // longitude
            = 0.0

    @SuppressLint("MissingPermission")
    fun getLocation(): Location? {
        try {
            locationManager = mContext
                .getSystemService(LOCATION_SERVICE) as LocationManager

            // getting GPS status
            isGPSEnabled = locationManager!!
                .isProviderEnabled(LocationManager.GPS_PROVIDER)

            // getting network status
            isNetworkEnabled = locationManager!!
                .isProviderEnabled(LocationManager.NETWORK_PROVIDER)
            if (!isGPSEnabled && !isNetworkEnabled) {
                // no network provider is enabled
            } else {
                canGetLocation = true
                // First get location from Network Provider
                if (isNetworkEnabled) {
                    locationManager!!.requestLocationUpdates(
                        LocationManager.NETWORK_PROVIDER,
                        MIN_TIME_BW_UPDATES,
                        MIN_DISTANCE_CHANGE_FOR_UPDATES.toFloat(), this
                    )
                    Log.d("Network", "Network")
                    if (locationManager != null) {
                        location = locationManager!!
                            .getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
                        if (location != null) {
                            latitude = location!!.latitude
                            longitude = location!!.longitude
                        }
                    }
                }
                // if GPS Enabled get lat/long using GPS Services
                if (isGPSEnabled) {
                    if (location == null) {
                        locationManager!!.requestLocationUpdates(
                            LocationManager.GPS_PROVIDER,
                            MIN_TIME_BW_UPDATES,
                            MIN_DISTANCE_CHANGE_FOR_UPDATES.toFloat(), this
                        )
                        Log.d("GPS Enabled", "GPS Enabled")
                        if (locationManager != null) {
                            location = locationManager!!
                                .getLastKnownLocation(LocationManager.GPS_PROVIDER)
                            if (location != null) {
                                latitude = location!!.latitude
                                longitude = location!!.longitude
                            }
                        }
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return location
    }

    /**
     * Stop using GPS listener
     * Calling this function will stop using GPS in your app
     */
    fun stopUsingGPS() {
        if (locationManager != null) {
            locationManager!!.removeUpdates(this@GPSUtility)
        }
    }

    /**
     * Function to get latitude
     */
    fun getLatitude(): Double {
        if (location != null) {
            latitude = location!!.latitude
        }

        // return latitude
        return latitude
    }

    /**
     * Function to get longitude
     */
    fun getLongitude(): Double {
        if (location != null) {
            longitude = location!!.longitude
        }

        // return longitude
        return longitude
    }

    /**
     * Function to check GPS/wifi enabled
     *
     * @return boolean
     */
    fun canGetLocation(): Boolean {
        return canGetLocation
    }

    /**
     * Function to show settings alert dialog
     * On pressing Settings button will lauch Settings Options
     */
    fun showSettingsAlert() {
        val alertDialog = AlertDialog.Builder(mContext)

        // Setting Dialog Title
//        alertDialog.setTitle("GPS needed")
        alertDialog.setTitle(mContext.getString(R.string.title_dialog_alert))

        // Setting Dialog Message
//        alertDialog.setMessage("GPS is not enabled. Do you want to go to settings menu?")
        alertDialog.setMessage(mContext.getString(R.string.text_dialog_ask_enable_gps))

        // On pressing Settings button
        alertDialog.setPositiveButton(
            mContext.getString(R.string.buttonActivate)
        ) { dialog, which ->
            val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
            mContext.startActivity(intent)
        }

        // on pressing cancel button
        alertDialog.setNegativeButton(
            mContext.getString(R.string.buttonNegative)
        ) { dialog, which -> dialog.cancel() }

        // Showing Alert Message
        alertDialog.show()
    }

    /**
     * Gets the current GPS location. If the user doesn't have GPS enabled pops
     * up an alert dialog allowing the user to access settings and enable GPS.
     * If GPS still isn't enabled, pops up an alert dialog displaying an error.
     *
     * @return the current GPS location of the user.
     */
    //	    public Location promptForGPS() {
    //	    	if (!canGetLocation())
    //	    		showSettingsAlert();
    //
    //	    	if (canGetLocation())
    //	    		return getLocation();
    //	    	else {
    //	    		AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
    //	    		builder
    //	    			.setTitle("Error")
    //	    			.setMessage("No GPS enabled")
    //	    			.setPositiveButton("Okay", null)
    //	    			.show();
    //	    		return null;
    //	    	}
    //	    }
    fun promptForGPS(): Location? {
        if (!canGetLocation()) showSettingsAlert()
        return if (canGetLocation()) getLocation() else null

//        if (canGetLocation())
//            return getLocation();
//        else {
//            AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
//            builder
//                    .setTitle("Error")
//                    .setMessage("No GPS enabled")
//                    .setPositiveButton("Okay", null)
//                    .show();
//            return null;
//        }
    }

    override fun onLocationChanged(location: Location) {}
    override fun onProviderDisabled(provider: String) {}
    override fun onProviderEnabled(provider: String) {}
    override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}
    override fun onBind(arg0: Intent): IBinder? {
        return null
    }

    companion object {
        /**
         * Miles per meter.
         */
        // This is a float since Location.distanceTo returns a float
        private const val MILES_PER_METER = 0.000621371f

        // The minimum distance to change Updates in meters
        private const val MIN_DISTANCE_CHANGE_FOR_UPDATES: Long = 10 // 10 meters

        // The minimum time between updates in milliseconds
        private const val MIN_TIME_BW_UPDATES = (1000 * 60 * 1 // 1 minute
                ).toLong()

        /**
         * Calculates the distance between two latitude, longitude coordinates and
         * returns the result in miles rounded to one digit after the decimal place.
         *
         * @param lat1  Latitude of first coordinate.
         * @param long1 Longitude of first coordinate.
         * @param lat2  Latitude of second coordinate.
         * @param long2 Longitude of second coordinate.
         * @return the distance in miles between the two latitude longitude points.
         */
        fun calculateGPSDistance(lat1: Double, long1: Double, lat2: Double, long2: Double): Double {
            val results = FloatArray(1)
            Location.distanceBetween(lat1, long1, lat2, long2, results)
            return round((results[0] * MILES_PER_METER).toDouble(), 1)
        }

        /**
         * Rounds double to a specified number of places after the decimal.
         *
         * @param value  Value being rounded.
         * @param places Places after the decimal to round to.
         * @return value rounded to the specified number of places after the decimal.
         */
        private fun round(value: Double, places: Int): Double {
            var value = value
            val factor = Math.pow(10.0, places.toDouble()).toLong()
            value *= factor
            val tmp = Math.round(value)
            return tmp.toDouble() / factor
        }
    }

    init {
        getLocation()
    }
}
