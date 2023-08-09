package com.example.project2_info_6124

import android.Manifest
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.project2_info_6124.ui.email.EmailFragment
import com.google.android.gms.location.LocationServices
import java.io.IOException
import java.util.*

class LocationUtils(private val fragment: EmailFragment) {

    interface LocationListener {
        fun onLocationResult(location: Location)
    }

    private val geocoder = Geocoder(fragment.requireContext(), Locale.getDefault())

    fun getCurrentLocation(listener: LocationListener) {
        if (ContextCompat.checkSelfPermission(
                fragment.requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // Permission not granted, request it
            ActivityCompat.requestPermissions(
                fragment.requireActivity(),
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_REQUEST_CODE
            )
            return
        }

        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(fragment.requireContext())
        fusedLocationClient.lastLocation.addOnSuccessListener { location ->
            if (location != null) {
                listener.onLocationResult(location)
            }
        }
    }

    fun getAddressFromLocation(location: Location): String {
        try {
            val addresses: List<Address>? =
                geocoder.getFromLocation(location.latitude, location.longitude, 1)
            if (addresses != null && addresses.isNotEmpty()) {
                val address = addresses[0]
                return address.getAddressLine(0) ?: ""
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return ""
    }

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 123
    }
}
