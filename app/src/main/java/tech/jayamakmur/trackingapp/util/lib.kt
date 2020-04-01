package tech.jayamakmur.trackingapp.util

import android.content.Context
import android.location.Address
import android.location.Geocoder
import tech.jayamakmur.trackingapp.model.MyLatLng
import java.util.*

fun getAddress(context: Context, latLng: MyLatLng): Address {
    val geoCoder = Geocoder(context, Locale.getDefault())
    return geoCoder.getFromLocation(latLng.latitude, latLng.longitude, 1)[0]
}