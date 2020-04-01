package tech.jayamakmur.trackingapp.ui.maps


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.fragment_maps.*
import tech.jayamakmur.trackingapp.R
import tech.jayamakmur.trackingapp.model.Device


class MapsFragment : Fragment() {
    private var googleMap: GoogleMap? = null
    var data: Device? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_maps, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mapView.onCreate(savedInstanceState)
        mapView.onResume()
        mapView.getMapAsync {
            it?.let { gMaps ->
                gMaps.uiSettings.setAllGesturesEnabled(false)
                this.googleMap = gMaps
            }
        }
    }


    var first = true
    fun updateMapsPin(latLng: LatLng) {
        googleMap?.let { googleMap ->
            googleMap.clear()
            googleMap.addMarker(MarkerOptions().position(latLng))
            if (first) {
                first = false
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 17f))
            } else googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 17f))
        }
    }
}
