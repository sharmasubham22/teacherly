package ca.dal.teacherly.ui.Subjects

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ca.dal.teacherly.controllers.SubjectController
import ca.dal.teacherly.databinding.FragmentSubjectsBinding

/*
 * @author Bharatwaaj Shankaranarayanan
 * @description Subjects Screen Fragment to list and display all the subjects
 */
class SubjectsFragment : Fragment() {

    // Creating a binding object to bind data from the fragment and take reference
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

        // Call the Subject Controller to initialize subjects during onCreate of the fragment
        SubjectController.initializeSubjectsFromFirebase(_binding!!, this.context)

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}