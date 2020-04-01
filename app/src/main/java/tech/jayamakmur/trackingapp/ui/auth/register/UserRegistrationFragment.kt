package tech.jayamakmur.trackingapp.ui.auth.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_user_registration.view.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import tech.jayamakmur.trackingapp.databinding.FragmentUserRegistrationBinding
import tech.jayamakmur.trackingapp.model.User
import tech.jayamakmur.trackingapp.repository.Repository
import tech.jayamakmur.trackingapp.ui.MainActivity


class UserRegistrationFragment : Fragment() {

    private val viewModel: ViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (requireActivity() as MainActivity).setNavigationVisible(false)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        FragmentUserRegistrationBinding.inflate(inflater).apply {
            viewModel = this@UserRegistrationFragment.viewModel
            lifecycleOwner = viewLifecycleOwner
        }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.ID_UserRegistration_Next.setOnClickListener {
            lifecycleScope.launch(IO) {
                Repository.setUser(viewModel.user.value!!)
                (requireActivity() as MainActivity).restart()
            }
        }
    }

    class ViewModel : androidx.lifecycle.ViewModel() {
        val user = MutableLiveData<User>()

        init {
            FirebaseAuth.getInstance().currentUser?.let {
                user.value = User().fromFirebaseUser(it)
            }
        }
    }
}