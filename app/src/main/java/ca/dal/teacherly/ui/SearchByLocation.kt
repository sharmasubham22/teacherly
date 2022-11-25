package ca.dal.teacherly.ui

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
import ca.dal.teacherly.models.LocationModel
import ca.dal.teacherly.models.Tutor
import ca.dal.teacherly.utils.DatabaseSingleton
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import kotlinx.android.synthetic.main.fragment_search_by_location.*
import kotlinx.android.synthetic.main.fragment_search_by_location.view.*

class SearchByLocation : Fragment() {

    private lateinit var currentLocation: Location
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    private val permissionCode = 101

    private lateinit var mapCircle: Circle
    private lateinit var circle: CircleOptions

    private var teacherList: ArrayList<Tutor>? = arrayListOf()

    //    var startPoint = 0
//    var endPoint = 0
//    private var locationArrayList = ArrayList<LatLng>()

//    var TamWorth = LatLng(-31.083332, 150.916672)
//    var NewCastle = LatLng(-32.916668, 151.750000)
//    var Brisbane = LatLng(-27.470125, 153.021072)

    private val callback = OnMapReadyCallback { googleMap ->

//        locationArrayList!!.add(TamWorth)
//        locationArrayList!!.add(NewCastle)
//        locationArrayList!!.add(Brisbane)


//        locationArrayList.add(LatLng(44.6363833242448, -63.587331330932855))
//        locationArrayList.add(LatLng(44.63698644071133, -63.58929470783781))

        val userLatLng = LatLng(currentLocation.latitude, currentLocation.longitude)
        val userLocationMarker = MarkerOptions().position(userLatLng).title("Current location")
            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))

        googleMap.animateCamera(CameraUpdateFactory.newLatLng(userLatLng))
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(userLatLng, 15f))
        googleMap.addMarker(userLocationMarker)

        circle = CircleOptions().center(userLatLng).radius(RADIUS).strokeColor(Color.RED)
        mapCircle = googleMap.addCircle(circle)

        val markerOptions = ArrayList<MarkerOptions>()

//        Log.d("TeacherList ","${teacherList!![0]}")
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
//            .addOnFailureListener {
//
//            }
        return view
    }

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
    }
}