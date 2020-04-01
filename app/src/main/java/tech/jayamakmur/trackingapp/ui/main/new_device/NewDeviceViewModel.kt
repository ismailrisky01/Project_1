package tech.jayamakmur.trackingapp.ui.main.new_device

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.findNavController
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import org.jetbrains.anko.design.snackbar
import tech.jayamakmur.jmdlibrary.lib.MyBarcodeScanner
import tech.jayamakmur.jmdlibrary.lib.MyImagePicker
import tech.jayamakmur.jmdlibrary.util.logD
import tech.jayamakmur.trackingapp.R
import tech.jayamakmur.trackingapp.model.Device
import tech.jayamakmur.trackingapp.model.GlobalVariable
import tech.jayamakmur.trackingapp.repository.Repository
import tech.jayamakmur.trackingapp.util.MyDialog

class NewDeviceViewModel : ViewModel() {
    val device = MutableLiveData(Device())

    fun scanDeviceID(view: View) {
        MyBarcodeScanner(view.context).start {
            device.postValue(device.value?.apply { id = it })
        }
    }

    fun changeBrand(view: View) {
        val list = arrayListOf(R.drawable.ic_honda, R.drawable.ic_yamaha, R.drawable.ic_suzuki)
        val picker = MyImagePicker<Int>(view.context).setList(list)
        device.value?.icon?.let { picker.setDefault(it) }
        picker.show().onPositiveListener { _, value ->
            device.postValue(device.value?.apply { icon = value })
        }
    }

    fun changeColor(view: View) {
        val picker = MyImagePicker<Int>(view.context).setList(GlobalVariable.colorList).setType(MyImagePicker.COLOR)
        device.value?.icon?.let { picker.setDefault(it) }
        picker.show().onPositiveListener { _, value ->
            device.postValue(device.value?.apply { color = value })
        }
    }

    private fun validate(view: View, device: Device): Boolean {
        val flag = when {
            device.id == "" -> "Please Scan Device QR Code"
            device.color == R.color.colorPrimary -> "Please Choose Vehicle Color"
            device.brand == "" -> "Please Choose Vehicle Brand"
            device.icon == R.drawable.ic_jmd_light -> "Please Choose Vehicle Icon"
            else -> ""
        }
        logD(flag)
        return flag == ""
    }

    fun register(view: View) {
        if (validate(view, device.value!!)) viewModelScope.launch(Main) {
            val loading = MyDialog(view.context).showLoading()
            val devices = GlobalVariable.user.value?.devices
            val device = device.value
            if (devices != null && device != null) {
                devices[device.id] = device
                Repository.newDeviceList(devices)
                loading.dismiss()
                view.findNavController().navigate(NewDeviceFragmentDirections.actionNewDeviceFragmentToDeviceOverviewFragment(device.id))
            } else {
                loading.dismiss()
                view.snackbar("Error")
            }
        }
    }
}