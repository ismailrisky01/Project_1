package tech.jayamakmur.trackingapp.model

import android.os.Parcelable
import kotlinx.android.parcel.IgnoredOnParcel
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DeviceApi(
    var id: String,
    var latLng: MyLatLng,
    var ring: Boolean,
    var engine: Boolean
) : Parcelable {
    @IgnoredOnParcel
    val lastUpdate = System.currentTimeMillis()

    constructor() : this("", MyLatLng(0.0, 0.0), false, false)
}