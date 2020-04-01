package tech.jayamakmur.trackingapp.ui.auth.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_login.view.*
import org.jetbrains.anko.design.snackbar
import tech.jayamakmur.trackingapp.databinding.FragmentLoginBinding
import tech.jayamakmur.trackingapp.ui.MainActivity
import tech.jayamakmur.trackingapp.util.GoogleAuth

class LoginFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (requireActivity() as MainActivity).setNavigationVisible(false)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return FragmentLoginBinding.inflate(inflater).root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.ID_Login_GoogleSignIn.setOnClickListener {
            GoogleAuth(requireActivity()).signIn(requireActivity() as MainActivity) {
                if (it != null) (requireActivity() as MainActivity).restart()
                else view.snackbar("Google Login Failed")
            }
        }
    }

}