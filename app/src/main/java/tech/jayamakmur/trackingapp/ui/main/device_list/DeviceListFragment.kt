package tech.jayamakmur.trackingapp.ui.main.device_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_device_list.*
import tech.jayamakmur.trackingapp.databinding.FragmentDeviceListBinding
import tech.jayamakmur.trackingapp.model.GlobalVariable

class DeviceListFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return FragmentDeviceListBinding.inflate(inflater).root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ID_DeviceList_RecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = DeviceListAdapter(arrayListOf()).apply { setHasStableIds(true) }
        }
        GlobalVariable.user.observe(viewLifecycleOwner, Observer { user ->
            ID_DeviceList_RecyclerView?.let {
                (it.adapter as DeviceListAdapter).apply {
                    list = user.devices.values.toList()
                    notifyDataSetChanged()
                }
            }
        })
    }
}