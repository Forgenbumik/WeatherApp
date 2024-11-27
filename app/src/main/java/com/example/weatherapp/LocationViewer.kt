package com.example.weatherapp

import android.content.Context
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.util.Log

interface LocationCallback {
    fun onLocationReceived(latitude: Double, longitude: Double)
}

class LocationViewer(private val context: Context) {
    private var fusedLocationClient: FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)

    var latitude: Double = 0.0

    var longitude: Double = 0.0

    fun getLastKnownLocation(callback: LocationCallback) {
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            val permissions = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION)
            ActivityCompat.requestPermissions(context as Activity, permissions,0)
        }
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location->
                if (location != null) {
                    Log.d("LocationViewer", "Location obtained: Lat=$latitude, Lng=$longitude")
                    callback.onLocationReceived(location.latitude, location.longitude)

                } else {
                    Log.e("LocationViewer", "Location is null")
                }
            }
            .addOnFailureListener { exception ->
                Log.e("LocationViewer", "Error getting location: ${exception.message}", exception)
            }
    }

}