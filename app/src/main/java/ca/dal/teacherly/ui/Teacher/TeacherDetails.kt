package ca.dal.teacherly.ui.Teacher

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import androidx.navigation.Navigation
import ca.dal.teacherly.R
import ca.dal.teacherly.ui.Student.AddFeedbackDirections

class TeacherDetails : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_teacher_details, container, false)

        //val bookAppointmentBtn = view.findViewById<Button>(R.id.bookAppointmentBtn1)
        val bookAppointmentBtn : Button = view.findViewById(R.id.appointmentBtn23)

        bookAppointmentBtn.setOnClickListener{
            val navController = Navigation.findNavController(view)
            val action = TeacherDetailsDirections.actionTeacherDetailsToBookAppointment()
            navController.navigate(action)
        }

        val feedbackBtn = view.findViewById<Button>(R.id.feedbackBtn)

        feedbackBtn.setOnClickListener{
            val navController = Navigation.findNavController(view)
            val action = TeacherDetailsDirections.actionTeacherDetailsToAddFeedback()
            navController.navigate(action)
        }

        val teacherDetailBackBtn : ImageButton = view.findViewById(R.id.teacherDetailBackBtn)

        teacherDetailBackBtn.setOnClickListener{
            val navController = Navigation.findNavController(view)
            val action = TeacherDetailsDirections.actionTeacherDetailsToNavigationHomeFragment()
            navController.navigate(action)
        }

        return view
    }
}