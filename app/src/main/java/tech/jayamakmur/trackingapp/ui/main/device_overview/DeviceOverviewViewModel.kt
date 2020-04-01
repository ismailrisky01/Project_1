package tech.jayamakmur.trackingapp.ui.main.device_overview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import kotlinx.coroutines.ExperimentalCoroutinesApi
import tech.jayamakmur.jmdlibrary.util.logD
import tech.jayamakmur.trackingapp.model.Device
import tech.jayamakmur.trackingapp.model.DeviceApi
import tech.jayamakmur.trackingapp.repository.Repository


@ExperimentalCoroutinesApi
class DeviceOverviewViewModel : ViewModel() {
    val deviceDetail = MutableLiveData<Device>()
    lateinit var deviceApi: LiveData<DeviceApi?>
    val location = MutableLiveData("")

    fun setDevice(id: String) {
        deviceApi = Repository.getDevice(id).asLiveData()
        deviceApi.observeForever {}
    }

    fun onRingClick(state: Boolean) {
        Repository.realtime.child(deviceApi.value!!.id).child("ring").setValue(state)
    }
}