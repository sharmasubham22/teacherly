package ca.dal.teacherly.ui.Menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import ca.dal.teacherly.R
import ca.dal.teacherly.databinding.FragmentMenuBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_menu.*


class NotificationsFragment : Fragment() {


    private var _binding: FragmentMenuBinding? = null
    private lateinit var textV : TextView
    private lateinit var phone : TextView
    private lateinit var name : TextView

    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

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

//        val navigationView: NavigationView = homeMenu
//        navigationView.setOnClickListener{
//            when(it.id){
//                R.id.edit_profile -> {
//                    val bundle = Bundle()
//                    bundle.putString("Email", receivedEmail)
//                    findNavController().navigate(R.id.edit_profile,
//                        bundle)
//                    true
//                }
//                else -> false
//            }
//        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}