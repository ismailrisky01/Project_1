package tech.jayamakmur.trackingapp.util

import android.app.Application
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import tech.jayamakmur.trackingapp.model.MyLatLng
import kotlin.random.Random


class App : Application() {

    override fun onCreate() {
        super.onCreate()
        deviceSimulation()
    }

    private fun deviceSimulation() {
        CoroutineScope(Dispatchers.IO).launch {
            startSimulation("1585666474715", MyLatLng(-7.479150, 111.407810))
        }
        CoroutineScope(Dispatchers.IO).launch {
            delay(1000)
            startSimulation("-M3ll-OmcKERm4yExl7G", MyLatLng(-7.410783, 111.443334))
        }
        CoroutineScope(Dispatchers.IO).launch {
            delay(2000)
            startSimulation("-M3lkzh-wckakvdx36De", MyLatLng(-7.404315, 111.444410))
        }
        CoroutineScope(Dispatchers.IO).launch {
            delay(3000 )
            startSimulation("-M3lkytWlduOjH8z4IzW", MyLatLng(-7.394012, 111.451756))
        }
    }

    private suspend fun startSimulation(id: String, latLng: MyLatLng) {
        delay(4000)
        simulateDevice(id, latLng)
    }

    private suspend fun simulateDevice(id: String, latLng: MyLatLng) {
        FirebaseDatabase.getInstance().reference.child(id).apply {
            child("latLng").setValue(latLng)
            child("lastUpdate").setValue(System.currentTimeMillis())
        }
        val newLatLng = MyLatLng(
            latLng.latitude.plus(Random(System.currentTimeMillis()).nextDouble(-0.0001, 0.0001)),
            latLng.longitude.plus(Random(System.currentTimeMillis()).nextDouble(-0.0001, 0.0001))
        )
        startSimulation(id, newLatLng)
    }

}