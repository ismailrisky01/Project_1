package tech.jayamakmur.trackingapp.model

import androidx.lifecycle.MutableLiveData
import tech.jayamakmur.trackingapp.R

object GlobalVariable {
    var user = MutableLiveData<User>()

    val colorList = arrayListOf(R.color.red, R.color.green, R.color.blue, R.color.black, R.color.white)

}