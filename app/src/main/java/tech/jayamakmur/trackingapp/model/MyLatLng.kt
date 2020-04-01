package tech.jayamakmur.trackingapp.model

import android.os.Parcelable
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.firestore.Exclude
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MyLatLng(var latitude: Double, var longitude: Double) : Parcelable {
    constructor() : this(0.0, 0.0)

    @Exclude
    fun googleLatLng() = LatLng(latitude, longitude)
}