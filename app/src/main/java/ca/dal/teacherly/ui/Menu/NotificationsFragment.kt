package ca.dal.teacherly.ui.Menu

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import ca.dal.teacherly.R
import ca.dal.teacherly.databinding.FragmentMenuBinding
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_menu.*


class NotificationsFragment : Fragment() {


    private var _binding: FragmentMenuBinding? = null
    private lateinit var textV : TextView
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var db: FirebaseFirestore


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentMenuBinding.inflate(inflater, container, false)
        val root: View = binding.root

        textV = root.findViewById(R.id.phoneNumber)
        val data = arguments
        Log.v("Data", data.toString())

        //textV.text = data?.get("Email").toString()
        //Log.v("Passed Email", email.toString())

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}