package tech.jayamakmur.trackingapp.ui.main.device_list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import tech.jayamakmur.trackingapp.R
import tech.jayamakmur.trackingapp.databinding.FragmentDeviceListItemBinding
import tech.jayamakmur.trackingapp.model.Device
import tech.jayamakmur.trackingapp.repository.Repository
import tech.jayamakmur.trackingapp.util.getAddress

class DeviceListAdapter(var list: List<Device>) : RecyclerView.Adapter<DeviceListAdapter.ViewHolder>() {

    override fun getItemCount() = list.size

    override fun getItemId(position: Int): Long = list[position].id.hashCode().toLong()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        FragmentDeviceListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        holder.binding.viewModel = item
        holder.binding.IDDeviceListItemIcon.setImageResource(item.icon)
        var lastAddress = ""
        GlobalScope.launch(IO) {
            Repository.getDevice(item.id).collect {
                val address = getAddress(holder.itemView.context, it!!.latLng)
                val newAddress = "${address.thoroughfare}, ${address.locality}, ${address.subAdminArea}".replace("NULL,", "")
                if (lastAddress != newAddress) {
                    holder.binding.location = newAddress
                    lastAddress = newAddress
                }
            }
        }
        holder.itemView.setOnClickListener {
            it.findNavController().navigate(R.id.action_deviceListFragment_to_deviceOverviewFragment, bundleOf("ID" to item.id))
        }
    }

    class ViewHolder(val binding: FragmentDeviceListItemBinding) : RecyclerView.ViewHolder(binding.root)

    fun getDeviceLocation(){

    }
}