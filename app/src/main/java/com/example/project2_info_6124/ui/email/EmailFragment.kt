package com.example.project2_info_6124.ui.email

import android.content.ActivityNotFoundException
import android.content.Intent
import android.location.Location
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.project2_info_6124.LocationUtils
import com.example.project2_info_6124.R

class EmailFragment : Fragment() {
    private lateinit var locationUtils: LocationUtils
    private var currentLatitude = 0.0
    private var currentLongitude = 0.0
    private var currentAddress = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        locationUtils = LocationUtils(this)
        fetchCurrentLocation()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_email, container, false)
        val btnsendemail = view.findViewById<View>(R.id.btn_send_email)
        val sendemail = view.findViewById<EditText>(R.id.edit_email)
        btnsendemail.setOnClickListener {
            val emailAddress = sendemail.text.toString().trim()

            // Create the subject and email body with location information
            val subject = "Current Location Information"
            val body = "Latitude: ${currentLatitude}\nLongitude: ${currentLongitude}\nAddress: $currentAddress"

            // Create an email intent and prepopulate with subject, body, and recipient
            val emailIntent = Intent(Intent.ACTION_SENDTO)
            emailIntent.data = Uri.parse("mailto:$emailAddress" + "?subject=" + Uri.encode(subject) + "&body=" + Uri.encode(body))
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject)
            emailIntent.putExtra(Intent.EXTRA_TEXT, body)

            if (emailIntent.resolveActivity(requireActivity().packageManager) != null) {
                try {
                    context?.startActivity(Intent.createChooser(emailIntent, subject));
                } catch (e: ActivityNotFoundException) {
                    Toast.makeText(
                        this.requireContext(),
                        "There is no email client installed.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
        return view
    }

    private fun fetchCurrentLocation() {
        locationUtils.getCurrentLocation(object : LocationUtils.LocationListener {
            override fun onLocationResult(location: Location) {
                currentLatitude = location.latitude
                currentLongitude = location.longitude
                 currentAddress = locationUtils.getAddressFromLocation(location)

                // Now you have the latitude, longitude, and address
                // You can use them as needed
            }
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}
