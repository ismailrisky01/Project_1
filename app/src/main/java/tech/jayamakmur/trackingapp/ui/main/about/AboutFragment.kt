package tech.jayamakmur.trackingapp.ui.main.about

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import kotlinx.android.synthetic.main.fragment_about.*
import tech.jayamakmur.trackingapp.databinding.FragmentAboutBinding
import tech.jayamakmur.trackingapp.model.GlobalVariable
import tech.jayamakmur.trackingapp.model.User
import tech.jayamakmur.trackingapp.ui.MainActivity
import tech.jayamakmur.trackingapp.util.GoogleAuth

class AboutFragment : Fragment() {

    private val viewModel: AboutViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        GlobalVariable.user.observe(this, Observer {
            viewModel.user.value = it
        })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return FragmentAboutBinding.inflate(inflater).apply {
            viewModel = this@AboutFragment.viewModel
            lifecycleOwner = viewLifecycleOwner
        }.root
    }

    override fun onStart() {
        super.onStart()
        ID_About_LogOut.setOnClickListener {
            GoogleAuth(it.context).signOut {
                (requireActivity() as MainActivity).restart()
            }
        }
    }

    class AboutViewModel : ViewModel() {
        val user = MutableLiveData<User>()
    }
}