package tech.jayamakmur.trackingapp.ui.auth.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import tech.jayamakmur.trackingapp.databinding.FragmentRegisterBinding
import tech.jayamakmur.trackingapp.ui.MainActivity

class RegisterFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (requireActivity() as MainActivity).setNavigationVisible(false)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        FragmentRegisterBinding.inflate(inflater).root
}