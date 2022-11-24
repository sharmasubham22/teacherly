package ca.dal.teacherly.ui.Subjects

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import ca.dal.teacherly.adapters.SubjectsAdapter
import ca.dal.teacherly.adapters.TutorsAdapter
import ca.dal.teacherly.controllers.SubjectController
import ca.dal.teacherly.data.InitialSubjects
import ca.dal.teacherly.data.InitialTutors
import ca.dal.teacherly.databinding.FragmentSubjectsBinding
import ca.dal.teacherly.models.Subject
import ca.dal.teacherly.utils.Constants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.time.Instant
import java.time.format.DateTimeFormatter

class SubjectsFragment : Fragment() {

    private var _binding: FragmentSubjectsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentSubjectsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        SubjectController.initializeSubjectsFromFirebase(auth, db, _binding!!, this.context)


        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}