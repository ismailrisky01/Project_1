package tech.jayamakmur.trackingapp.model

import android.os.Parcelable
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.Exclude
import kotlinx.android.parcel.IgnoredOnParcel
import kotlinx.android.parcel.Parcelize
import tech.jayamakmur.trackingapp.util.MyDataBindingUtil

@Parcelize
data class User(
    var uid: String,
    var fName: String,
    var lName: String,
    var email: String,
    var image: String,
    var devices: HashMap<String,Device>
) : Parcelable, MyDataBindingUtil {
    constructor() : this("", "", "", "", "", hashMapOf())

    fun fromFirebaseUser(user: FirebaseUser) = apply {
        uid = user.uid
        email = user.email.toString()
        fName = user.displayName.toString().split(" ")[0]
        lName = user.displayName.toString().replace("$fName ", "")
        image = user.photoUrl.toString()
    }

    @IgnoredOnParcel
    val fullName: String
        @Exclude get() = "$fName $lName"
}