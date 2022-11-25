package ca.dal.teacherly.ui.Home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ca.dal.teacherly.controllers.TutorController
import ca.dal.teacherly.databinding.FragmentHomeBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

/*
 * @author Bharatwaaj Shankaranarayanan
 * @description Home Screen Fragment to list and display all the teachers
 */
class HomeFragment : Fragment() {

    // Creating a binding object to bind data from the fragment and take reference
    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        val root: View = binding.root

        // Call the Tutor Controller to initialize tutors during onCreate of the fragment
        TutorController.initializeTutors(_binding!!, this.context)

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}