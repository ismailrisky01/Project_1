package tech.jayamakmur.trackingapp.ui.main.device_overview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.observe
import kotlinx.android.synthetic.main.fragment_device_overview.view.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import tech.jayamakmur.trackingapp.databinding.FragmentDeviceOverviewBinding
import tech.jayamakmur.trackingapp.model.GlobalVariable
import tech.jayamakmur.trackingapp.ui.maps.MapsFragment
import tech.jayamakmur.trackingapp.util.getAddress


@ExperimentalCoroutinesApi
class DeviceOverviewFragment : Fragment() {

    private val viewModel: DeviceOverviewViewModel by viewModels()

    init {
        lifecycleScope.launchWhenCreated {
            val id = requireArguments()["ID"] as String
            viewModel.setDevice(id)
            GlobalVariable.user.observe(this@DeviceOverviewFragment, Observer {
                viewModel.deviceDetail.value = (it.devices[id])
            })
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return FragmentDeviceOverviewBinding.inflate(inflater).apply {
            viewModel = this@DeviceOverviewFragment.viewModel
            lifecycleOwner = this@DeviceOverviewFragment
        }.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val maps = MapsFragment()
        childFragmentManager.beginTransaction().replace(view.ID_DeviceOverview_MapsContainer.id, maps).commit()
        viewModel.deviceDetail.observe(viewLifecycleOwner){
            view.ID_DeviceOverView_Icon.setImageResource(it.icon)
        }
        viewModel.deviceApi.observe(viewLifecycleOwner) {
            it?.let {
                maps.updateMapsPin(it.latLng.googleLatLng())
                val addresses = getAddress(requireContext(), it.latLng)
                val address = "${addresses.subLocality} - ${addresses.subAdminArea}"
                if (address != viewModel.location.value) viewModel.location.postValue(address)
            }
        }
    }
}