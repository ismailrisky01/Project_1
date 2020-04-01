package tech.jayamakmur.trackingapp.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import tech.jayamakmur.trackingapp.R

@Parcelize
data class Device(
    var id: String,
    var name: String,
    var detail: String,
    var plate: String,
    var color: Int,
    var year: Int,
    var type: String,
    var brand: String,
    var icon: Int
) : Parcelable {
    constructor() : this("", "", "", "", R.color.colorPrimary, 0, "", "", R.drawable.ic_jmd_light)


}