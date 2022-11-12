package ca.dal.teacherly.ui.Subjects

import android.os.Bundle
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

class SubjectsFragment : Fragment() {

    private var _binding: FragmentSubjectsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentSubjectsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        _binding!!.subjectsList.adapter = SubjectsAdapter(InitialSubjects.getAll())
        _binding!!.subjectsList.layoutManager = GridLayoutManager(this.context, 2, GridLayoutManager.VERTICAL, false)

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}