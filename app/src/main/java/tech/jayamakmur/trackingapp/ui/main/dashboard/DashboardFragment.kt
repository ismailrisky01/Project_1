package tech.jayamakmur.trackingapp.ui.main.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import kotlinx.android.synthetic.main.fragment_dashboard.*
import org.jetbrains.anko.design.snackbar
import tech.jayamakmur.trackingapp.R
import tech.jayamakmur.trackingapp.databinding.FragmentDashboardBinding
import tech.jayamakmur.trackingapp.ui.MainActivity

class DashboardFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (requireActivity() as MainActivity).setNavigationVisible(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return FragmentDashboardBinding.inflate(inflater).root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ID_Dashboard_NewItem.setOnClickListener {
            it.findNavController().navigate(R.id.newDeviceFragment)
        }
    }

}