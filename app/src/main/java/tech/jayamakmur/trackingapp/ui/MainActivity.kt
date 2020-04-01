package tech.jayamakmur.trackingapp.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import tech.jayamakmur.jmdlibrary.util.logD
import tech.jayamakmur.trackingapp.R
import tech.jayamakmur.trackingapp.model.GlobalVariable
import tech.jayamakmur.trackingapp.repository.Repository
import tech.jayamakmur.trackingapp.ui.maps.NotificationService

@ExperimentalCoroutinesApi
class MainActivity : AppCompatActivity() {

    private val viewModel: MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        databaseListener()
        NotificationService(this@MainActivity)
    }

    override fun onStart() {
        super.onStart()
        setupNavigation()
        onViewCreated()
    }

    private fun onViewCreated() {
        lifecycleScope.launch {
            val user = FirebaseAuth.getInstance().currentUser
            if (user != null) onUserLogin(findNavController(R.id.ID_MainNavigation))
            else onUserNotLogin(findNavController(R.id.ID_MainNavigation))
        }
    }

    private fun onUserLogin(nav: NavController) {
        GlobalVariable.user.observe(this, Observer {
            if (it == null) nav.navigate(R.id.action_dashboardFragment_to_userRegistrationFragment)
        })
    }

    private fun onUserNotLogin(nav: NavController) {
        nav.navigate(R.id.action_dashboardFragment_to_loginFragment)
    }

    private fun setupNavigation() {
        val navController = findNavController(R.id.ID_MainNavigation)
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        toolbar.setupWithNavController(navController, appBarConfiguration)
        ID_Main_BottomNav.setupWithNavController(findNavController(ID_MainNavigation.id))
        viewModel.isBottomNavigationShow.observe(this, Observer { visibility ->
            ID_Main_BottomNav?.let { it.visibility = if (visibility) View.VISIBLE else View.GONE }
            toolbar?.let { it.visibility = if (visibility) View.VISIBLE else View.GONE }
        })
    }

    private fun databaseListener() {
        FirebaseAuth.getInstance().currentUser?.let {
            Repository.init(it.uid)
            CoroutineScope(Dispatchers.IO).launch {
                Repository.getUser().collect { user ->
                    if (GlobalVariable.user.value != null && user == null) restart()
                    GlobalVariable.user.postValue(user)
                }
            }
        }
    }

    fun restart() {
        logD("Restart")
        finishAffinity()
        startActivity(intent)
    }

    fun setNavigationVisible(visible: Boolean) {
        viewModel.isBottomNavigationShow.value = visible
    }

    private var listener: (requestCode: Int, resultCode: Int, data: Intent?) -> Unit = { _, _, _ -> }
    fun setOnActivityResultListener(listener: (requestCode: Int, resultCode: Int, data: Intent?) -> Unit) {
        this.listener = listener
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        listener.invoke(requestCode, resultCode, data)
    }

    class MainActivityViewModel : ViewModel() {
        val isBottomNavigationShow = MutableLiveData(false)
    }
}