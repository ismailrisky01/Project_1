package tech.jayamakmur.trackingapp.ui.maps

import android.content.Context
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import tech.jayamakmur.trackingapp.model.Device
import tech.jayamakmur.trackingapp.model.DeviceApi
import tech.jayamakmur.trackingapp.model.GlobalVariable
import tech.jayamakmur.trackingapp.repository.Repository
import tech.jayamakmur.trackingapp.util.MyNotification

class NotificationService(val context: Context) {
    init {
        if (GlobalVariable.user.value != null)
            GlobalVariable.user.observeForever { user ->
                user.devices.forEach { (deviceID, device) ->
                    CoroutineScope(Dispatchers.IO).launch {
                        Repository.getDevice(deviceID).collect { deviceAPI ->
                            if (deviceAPI != null) {
                                onDeviceNotification(device, deviceAPI)
                            }
                        }
                    }
                }
            }
    }

    private val lastDeviceData = HashMap<Int, Device>()
    private val lastDeviceApiData = HashMap<Int, DeviceApi>()

    private val notification = MyNotification(context)

    private fun onDeviceNotification(device: Device, deviceApi: DeviceApi) {
        val id = device.hashCode()
        val title = "Device ${device.name}[${device.brand}]"
        when {
            deviceApi.ring && lastDeviceApiData[id]?.ring != deviceApi.ring ->
                notification.create(MyNotification.NotificationAdapter(id, title, "Ringin Device"))
        }
        lastDeviceData[id] = device
        lastDeviceApiData[id] = deviceApi
    }
}