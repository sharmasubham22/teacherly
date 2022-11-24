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
import ca.dal.teacherly.data.InitialSubjects
import ca.dal.teacherly.data.InitialTutors
import ca.dal.teacherly.databinding.FragmentSubjectsBinding
import ca.dal.teacherly.models.Subject
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

        var ref = db.collection("SUBJECTS").get();
        val subjects: ArrayList<Subject> = ArrayList();

        InitialSubjects.clearAll();

        ref.addOnSuccessListener {
            val size = it.documents.count()-1
            for (idx in 0..size){
                var subjectName = it.documents.get(idx).get("name")?.toString().toString();
                var subjectImageURL = it.documents.get(idx).get("imageURL")?.toString().toString()

                Log.d("Subject Name", subjectName)
                InitialSubjects.addTutor(
                    Subject(subjectName, DateTimeFormatter.ISO_INSTANT.format(Instant.now()), DateTimeFormatter.ISO_INSTANT.format(
                        Instant.now()), subjectImageURL)
                );
            }
            _binding!!.subjectsList.adapter = SubjectsAdapter(InitialSubjects.getAll())
            _binding!!.subjectsList.layoutManager = GridLayoutManager(this.context, 2, GridLayoutManager.VERTICAL, false)
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}