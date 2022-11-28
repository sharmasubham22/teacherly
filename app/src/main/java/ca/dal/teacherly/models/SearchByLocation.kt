package ca.dal.teacherly.models

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import ca.dal.teacherly.R
import ca.dal.teacherly.utils.DatabaseSingleton
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import kotlinx.android.synthetic.main.fragment_search_by_location.*
import kotlinx.android.synthetic.main.fragment_search_by_location.view.*

/***
 * @author Meet Master
 * @description This fragment is taking users location and displaying nearby teachers for the selected subject
 */


class SearchByLocation : Fragment() {

    private lateinit var currentLocation: Location
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    private lateinit var mapCircle: Circle
    private lateinit var circle: CircleOptions

    private var teacherList: ArrayList<Tutor>? = arrayListOf()

    // This function is using GoogleMap API for displaying map, user's location and nearby
    // registered teacher's location
    private val callback = OnMapReadyCallback { googleMap ->

        val userLatLng = LatLng(currentLocation.latitude, currentLocation.longitude)
        val userLocationMarker = MarkerOptions().position(userLatLng).title("Current location")
            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))

        googleMap.animateCamera(CameraUpdateFactory.newLatLng(userLatLng))
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(userLatLng, 15f))
        googleMap.addMarker(userLocationMarker)

        circle = CircleOptions().center(userLatLng).radius(RADIUS).strokeColor(Color.RED)
        mapCircle = googleMap.addCircle(circle)

        val markerOptions = ArrayList<MarkerOptions>()

        for (i in teacherList!!) {

            val result = FloatArray(3)

            Location.distanceBetween(
                userLatLng.latitude,
                userLatLng.longitude,
                i.latitude,
                i.longitude,
                result
            )

            println(result[0])

            //Checking if the registered teachers are in the location of current user's proximity
            if (result[0] <= RADIUS) {

                val teacherLatLong = LatLng(i.latitude, i.longitude)

                val markerOption =
                    MarkerOptions().position(teacherLatLong).title(i.tutorName)
                        .snippet(i.email + "\n" + i.mobileNumber + "\n" + i.costPerHour)
                googleMap.animateCamera(CameraUpdateFactory.newLatLng(teacherLatLong))
                googleMap.animateCamera(
                    CameraUpdateFactory.newLatLngZoom(
                        teacherLatLong,
                        11F
                    )
                )
                markerOptions.add(markerOption)
            }
        }

        //Displaying all the nearby teachers on the google map
        for (mo in markerOptions) {
            googleMap.addMarker(mo)?.showInfoWindow()
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_search_by_location, container, false)

        fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(this.requireActivity())

        val query = DatabaseSingleton.getUserReference().whereEqualTo("Type", "Teacher")

        query.get()
            .addOnSuccessListener { documents ->
                val locationModel = LocationModel()
                teacherList = locationModel.getTeacherList(documents)
                fetchLocation()
            }

        return view
    }

    //This function will fetch current user's location using mobile's GPS if users has provided
    // location permission
    @SuppressLint("MissingPermission")
    private fun fetchLocation() {
        if ((ActivityCompat.checkSelfPermission(
                this.requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED)
            && (ActivityCompat.checkSelfPermission(
                this.requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED)
        ) {

            ActivityCompat.requestPermissions(
                this.requireActivity(),
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ),
                permissionCode
            )
            return
        }

        val task = fusedLocationProviderClient.lastLocation

        task.addOnSuccessListener { location ->
            if (location != null) {
                currentLocation = location
                Toast.makeText(
                    this.requireContext(),
                    currentLocation.latitude.toString() + "" + currentLocation.longitude.toString(),
                    Toast.LENGTH_SHORT
                ).show()

                val mapFragment =
                    childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
                mapFragment?.getMapAsync(callback)

            }
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {

        when (requestCode) {
            permissionCode -> if (grantResults.isNotEmpty() && grantResults[0] ==
                PackageManager.PERMISSION_GRANTED
            ) {
                fetchLocation()
            }
        }
    }

    companion object {
        const val RADIUS = 10000.00
        const val permissionCode = 101
    }
}