package ca.dal.teacherly.ui.Home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import ca.dal.teacherly.adapters.SubjectsAdapter
import ca.dal.teacherly.adapters.TutorsAdapter
import ca.dal.teacherly.data.InitialSubjects
import ca.dal.teacherly.data.InitialTutors
import ca.dal.teacherly.databinding.FragmentHomeBinding
import ca.dal.teacherly.models.Subject
import ca.dal.teacherly.models.Tutor
import ca.dal.teacherly.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.time.Instant
import java.time.format.DateTimeFormatter

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

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

        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        val root: View = binding.root

        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        var ref = db.collection("USERS").get();
        val subjects: ArrayList<Subject> = ArrayList();

        InitialTutors.clearAll();

        ref.addOnSuccessListener {
            val size = it.documents.count()-1
            for (idx in 0..size){
                if(it.documents.get(idx).get("Type")?.toString().toString() == "Teacher"){

                    var name = it.documents.get(idx).get("Name")?.toString().toString();
//                    var hourlyRate = "%.2f".format(it.documents.get(idx).get("Latitude")?.toString().toString().toFloat())
                    var hourlyRate = "30.75"
                    var phone = it.documents.get(idx).get("Mobile Number")?.toString().toString();
                    var tutorImageURL = it.documents.get(idx).get("imageURL")?.toString().toString()

                    InitialTutors.addTutor(
                    Tutor(name, DateTimeFormatter.ISO_INSTANT.format(Instant.now()), DateTimeFormatter.ISO_INSTANT.format(
                            Instant.now()), phone, it.documents.get(idx).id, hourlyRate, tutorImageURL)
                    );

                }
            }
            _binding!!.homeTutorsList.adapter = TutorsAdapter(InitialTutors.getAll())
            _binding!!.homeTutorsList.layoutManager = GridLayoutManager(this.context, 2, GridLayoutManager.VERTICAL, false)
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}