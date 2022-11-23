package ca.dal.teacherly

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import ca.dal.teacherly.databinding.ActivityMainBinding
import ca.dal.teacherly.utils.LoginManager
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val sharedPref = this?.getPreferences(Context.MODE_PRIVATE) ?: return

        var email = intent.getStringExtra("Email")
        if (email != null) {
            with(sharedPref.edit()) {
                putString("Email", email)
                apply()
            }
        } else {
            var intent = Intent(applicationContext, LoginManager::class.java)
            startActivity(intent)
            finish()
        }

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bottomNavigationView: BottomNavigationView = findViewById(R.id.nav_view)
        bottomNavigationView.setOnItemSelectedListener { it ->
            when (it.itemId) {
                R.id.navigation_home -> {
                    findNavController(R.id.nav_host_fragment_activity_main).navigate(R.id.navigationHomeFragment)
                    true
                }
                R.id.navigation_dashboard -> {
                    findNavController(R.id.nav_host_fragment_activity_main).navigate(R.id.navigationDashboardFragment)
                    true
                }
                R.id.navigation_notifications -> {
                    val bundle = Bundle()
                    bundle.putString("Email", email.toString())
                    findNavController(R.id.nav_host_fragment_activity_main).navigate(
                        R.id.navigationFragment,
                        bundle
                    )
                    true
                }
                else -> false
            }
        }
    }
}