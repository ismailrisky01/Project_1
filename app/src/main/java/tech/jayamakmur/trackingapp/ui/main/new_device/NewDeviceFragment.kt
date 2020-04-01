package tech.jayamakmur.trackingapp.ui.main.new_device

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_new_device.view.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import tech.jayamakmur.jmdlibrary.util.getColorStateList
import tech.jayamakmur.trackingapp.databinding.FragmentNewDeviceBinding
import tech.jayamakmur.trackingapp.model.Device

@ExperimentalCoroutinesApi
class NewDeviceFragment : Fragment() {
    private val viewModel: NewDeviceViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return FragmentNewDeviceBinding.inflate(inflater).apply {
            viewModel = this@NewDeviceFragment.viewModel
            lifecycleOwner = viewLifecycleOwner
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.device.observe(viewLifecycleOwner) {  updateUI(view, it) }
    }

    private fun updateUI(view: View, device: Device) {
        view.ID_NewDevice_BrandImage_Container.setCardBackgroundColor(getColorStateList(requireContext(), device.color))
        Glide.with(view).load(device.icon).into(view.ID_NewDevice_BrandImage)
    }
}