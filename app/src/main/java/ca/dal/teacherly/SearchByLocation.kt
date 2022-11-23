package ca.dal.teacherly

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
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

    private var locationArrayList = ArrayList<LatLng>()

    var startPoint = 0
    var endPoint = 0

//    var TamWorth = LatLng(-31.083332, 150.916672)
//    var NewCastle = LatLng(-32.916668, 151.750000)
//    var Brisbane = LatLng(-27.470125, 153.021072)

    private val callback = OnMapReadyCallback { googleMap ->

//        locationArrayList!!.add(TamWorth)
//        locationArrayList!!.add(NewCastle)
//        locationArrayList!!.add(Brisbane)

        locationArrayList.add(LatLng(44.6363833242448, -63.587331330932855))
        locationArrayList.add(LatLng(44.63698644071133, -63.58929470783781))

        val latLng = LatLng(currentLocation.latitude, currentLocation.longitude)
        val userLocationMarker = MarkerOptions().position(latLng).title("Current location")
            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))

        googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng))
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f))
        googleMap.addMarker(userLocationMarker)

        val circle = CircleOptions().center(latLng).radius(1.00 * 1000).strokeColor(Color.RED)
        googleMap.addCircle(circle)

        val markerOptions = ArrayList<MarkerOptions>()

        for (i in locationArrayList.indices) {

            var result = FloatArray(3)

            Location.distanceBetween(
                latLng.latitude,
                latLng.longitude,
                locationArrayList[i].latitude,
                locationArrayList[i].longitude,
                result
            )

            println(result[0])

            if (result[0] <= (1 * 1000)) {

                val markerOption =
                    MarkerOptions().position(locationArrayList[i]).title("Professor " + (i + 1))
                googleMap.animateCamera(CameraUpdateFactory.newLatLng(locationArrayList[i]))
                googleMap.animateCamera(
                    CameraUpdateFactory.newLatLngZoom(
                        locationArrayList[i],
                        15f
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

        fetchLocation()

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        seekBar3.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progresss: Int, fromUser: Boolean) {
                editTextNumber.setText(progresss.toString())
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                startPoint = seekBar!!.progress
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                endPoint = seekBar!!.progress
            }
        })
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
}