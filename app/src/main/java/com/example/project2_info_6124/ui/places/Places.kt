package com.example.project2_info_6124.ui.places

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.ContentValues.TAG
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.project2_info_6124.R
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.tasks.Task
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.model.PlaceLikelihood
import com.google.android.libraries.places.api.net.FindCurrentPlaceRequest
import com.google.android.libraries.places.api.net.FindCurrentPlaceResponse
import com.google.android.libraries.places.api.net.PlacesClient


class PlacesFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private var mPlacesClient: PlacesClient? = null
    private val M_MAX_ENTRIES = 5


    private lateinit var mLikelyPlaceNames: Array<String>
    private lateinit var mLikelyPlaceAddresses: ArrayList<String>
    private lateinit var mLikelyPlaceLatLngs: ArrayList<LatLng>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val apiKey = getString(R.string.GOOGLE_MAP_API_KEY)
        Places.initialize(this.requireContext(), apiKey)
        mPlacesClient = Places.createClient(this.requireContext())
        mLikelyPlaceNames = arrayOf<String>("","","","","")
        mLikelyPlaceAddresses = ArrayList<String>(5)
        mLikelyPlaceLatLngs = ArrayList<LatLng>(5)

        getCurrentPlaceLikelihoods2()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_places, container, false)

        recyclerView = rootView.findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        return rootView
    }

    private fun getCurrentPlaceLikelihoods2() {
        val placeFields = listOf(Place.Field.NAME, Place.Field.ADDRESS)

        val request = FindCurrentPlaceRequest.builder(placeFields).build()
        val placeResponse: Task<FindCurrentPlaceResponse> =
            if (ActivityCompat.checkSelfPermission(
                    this.requireContext(),
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return
            } else {
                mPlacesClient!!.findCurrentPlace(request)
            }
        placeResponse.addOnCompleteListener(this.requireActivity()
        ) { task ->
            if (task.isSuccessful) {
                val response = task.result
                // Set the count, handling cases where less than 5 entries are returned.
                val count: Int
                if (response.placeLikelihoods.size < M_MAX_ENTRIES) {
                    count = response.placeLikelihoods.size
                } else {
                    count = M_MAX_ENTRIES
                }
                println("Found a place")
                var i = 0
                for (placeLikelihood: PlaceLikelihood in response.placeLikelihoods) {
                    val currPlace = placeLikelihood.place
                    mLikelyPlaceNames[i] = (currPlace.name as String)
                    currPlace.name?.let { Log.i(TAG, it) }
                    currPlace.address?.let { mLikelyPlaceAddresses.add(it) }
                    currPlace.latLng?.let { mLikelyPlaceLatLngs.add(it) }
                    i++
                    if (i > (count - 1)) {
                        break
                    }
                }

                println(mLikelyPlaceNames)

                val layoutManager = LinearLayoutManager(requireContext())
                val adapter = PlacesAdapter(mLikelyPlaceNames)

                recyclerView.layoutManager = layoutManager
                recyclerView.adapter = adapter
            } else {
                val exception: Exception? = task.getException()
                if (exception is ApiException) {
                    Log.e(TAG, "Place not found: " + exception.statusCode)
                }
            }
        }
    }
}
