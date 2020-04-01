package tech.jayamakmur.trackingapp.repository

import com.google.firebase.database.*
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import tech.jayamakmur.jmdlibrary.util.logD
import tech.jayamakmur.trackingapp.model.Device
import tech.jayamakmur.trackingapp.model.DeviceApi
import tech.jayamakmur.trackingapp.model.User

@ExperimentalCoroutinesApi
object Repository {
    lateinit var realtime: DatabaseReference
    lateinit var fireStore: DocumentReference

    private lateinit var uid: String

    fun init(uid: String) = apply {
        this.uid = uid
        realtime = FirebaseDatabase.getInstance().reference
        fireStore = FirebaseFirestore.getInstance().collection("User").document(Repository.uid)
    }

    fun getUser() = callbackFlow {
        val listener = fireStore.addSnapshotListener { result, error ->
            if (error == null && result != null) {
                offer(result.toObject(User::class.java))
            } else {
                logD(error?.message.toString())
            }
        }
        awaitClose { listener.remove() }
    }


    fun getDevice(id: String) = callbackFlow<DeviceApi?> {
        var open = true
        val listener = realtime.child(id).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (open) offer(snapshot.getValue(DeviceApi::class.java))
            }

            override fun onCancelled(p0: DatabaseError) {
                logD(p0)
            }
        })
        awaitClose {
            open = false
            realtime.removeEventListener(listener)
            close()
        }
    }

    suspend fun setUser(user: User) {
        fireStore.set(user).await()
    }

    suspend fun newDeviceList(devices: Map<String, Device>) {
        fireStore.update("devices", devices).await()
    }

    fun getNewID() = realtime.push().key!!

}