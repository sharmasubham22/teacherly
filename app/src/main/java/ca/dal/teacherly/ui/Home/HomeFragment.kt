package ca.dal.teacherly.ui.Home

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.widget.SearchView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import ca.dal.teacherly.R
import ca.dal.teacherly.adapters.TutorsAdapter
import ca.dal.teacherly.controllers.TutorController
import ca.dal.teacherly.data.InitialTutors
import ca.dal.teacherly.databinding.FragmentHomeBinding
import ca.dal.teacherly.models.Tutor

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

    private lateinit var tempTeacherList: ArrayList<Tutor>

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var searchView = view.findViewById<SearchView>(R.id.home_search_view)

        this.tempTeacherList = arrayListOf<Tutor>()
        tempTeacherList.addAll(InitialTutors.getAll())

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                Log.d("onQueryTextSubmit", "onQueryTextSubmit")
                return false
            }

            @SuppressLint("NotifyDataSetChanged")
            override fun onQueryTextChange(searchQuery: String?): Boolean {

                Log.d("onQueryTextChange", "onQueryTextChange")

                tempTeacherList.clear()
                val searchText = searchQuery!!.lowercase()

                if (searchText.isNotEmpty()) {
                    InitialTutors.getAll().forEach {
                        if (it.tutorName.lowercase().contains(searchText)) {
                            tempTeacherList.add(it)

                        }
                    }
                    _binding!!.homeTutorsList.adapter!!.notifyDataSetChanged()
                    _binding!!.homeTutorsList.adapter = TutorsAdapter(tempTeacherList)

                } else {
                    tempTeacherList.clear()
                    tempTeacherList.addAll(InitialTutors.getAll())
                    _binding!!.homeTutorsList.adapter!!.notifyDataSetChanged()
                    _binding!!.homeTutorsList.adapter = TutorsAdapter(tempTeacherList)

                }
                return false
            }
        })

    }
}