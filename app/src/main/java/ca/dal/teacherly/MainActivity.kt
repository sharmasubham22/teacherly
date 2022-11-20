package ca.dal.teacherly

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import ca.dal.teacherly.databinding.ActivityMainBinding
import ca.dal.teacherly.ui.Menu.NotificationsFragment
import ca.dal.teacherly.utils.LoginManager
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val sharedPref = this?.getPreferences(Context.MODE_PRIVATE)?:return
//        val isLogin = sharedPref.getString("Email","1")

        var email = intent.getStringExtra("Email")
        if(email!=null){
            with(sharedPref.edit()){
                putString("Email", email)
                apply()
            }

            Log.v("Email", email)

            val notifyFrag = NotificationsFragment()
            val notifyBundle = Bundle()
            notifyBundle.putString("Email", email.toString())
            notifyFrag.arguments = notifyBundle
            supportFragmentManager.beginTransaction().replace(R.id.navigation_notifications,notifyFrag)
                .commit()

        }else{
            var intent = Intent(applicationContext, LoginManager::class.java)
            startActivity(intent)
            finish()
        }

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }
}