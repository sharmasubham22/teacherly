package ca.dal.teacherly.ui.Menu

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import ca.dal.teacherly.MainActivity
import ca.dal.teacherly.R
import ca.dal.teacherly.databinding.FragmentMenuBinding
import ca.dal.teacherly.ui.Sessions.PastSessionsActivity
import ca.dal.teacherly.utils.*
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.auth.User
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_menu.*
import org.w3c.dom.Text
import kotlin.reflect.typeOf


class MenuFragment : Fragment(), NavigationView.OnNavigationItemSelectedListener {


    private var _binding: FragmentMenuBinding? = null
    // private lateinit var textV : TextView
    private lateinit var phone : TextView
    private lateinit var name : TextView
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    // private lateinit var navC : NavController
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentMenuBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val data = arguments
        val receivedEmail = data?.get("Email").toString()

        phone = root.findViewById(R.id.phoneNumber) as TextView
        name = root.findViewById(R.id.name) as TextView

        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        val ref = db.collection("USERS").document(receivedEmail)
        ref.get().addOnSuccessListener {
            if(it!=null){
                val fetchedName = it.data?.get("Name")?.toString()
                val fetchedPhoneNumber = it.data?.get("Mobile Number")?.toString()
                println("Fetched User name: $fetchedName")
                name.text = fetchedName
                phone.text = fetchedPhoneNumber
            }
        }

        val navigationView: NavigationView = root.findViewById(R.id.homeMenu)
        navigationView.setNavigationItemSelectedListener(this)
        navigationView.setOnClickListener{
            when(it.id){
                R.id.edit_profile -> {
                    val bundle = Bundle()
                    bundle.putString("Email", receivedEmail)
                    println("Edit Profile: $receivedEmail")
                    findNavController().navigate(R.id.edit_profile,
                        bundle)
                    true
                }

                R.id.my_assignments -> {
                    findNavController().navigate(R.id.my_assignments)
                    true
                }
                else -> false
            }
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.edit_profile -> {
                val data = arguments
                val receivedEmail = data?.get("Email").toString()
                val intent = Intent(activity, EditProfile::class.java)
                intent.putExtra("Email", receivedEmail)
                startActivity(intent)
                true
            }
            R.id.session_bookings -> {
                val intent = Intent(activity, PastSessionsActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.my_assignments ->{
                val intent = Intent(activity, AssignmentView::class.java)
                startActivity(intent)
                true
            }
            R.id.logout -> {
                Firebase.auth.signOut()
                val intent = Intent(activity, LoginManager::class.java)
                startActivity(intent)
                auth.signOut()
                true
            }
        }

        return true
    }
}

