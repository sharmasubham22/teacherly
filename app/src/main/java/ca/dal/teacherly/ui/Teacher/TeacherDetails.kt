package ca.dal.teacherly.ui.Teacher

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.Navigation
import ca.dal.teacherly.R
import com.squareup.picasso.Picasso

class TeacherDetails : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_teacher_details, container, false)

        val email = arguments?.get("teacherEmail")
        println("teacher email $email");

        val textView : TextView = view.findViewById(R.id.TeacherName)
        textView.text = arguments?.get("teacherName").toString()

        val teacherImage: ImageView = view.findViewById(R.id.teacherProfilePic)
        Picasso.get()
            .load(arguments?.get("teacherImageURL").toString())
            .resize(64,64)
            .into(teacherImage);

        val rate : TextView = view.findViewById(R.id.rate)
        rate.text = arguments?.get("teacherCost").toString()+"$"

        val subjects : TextView = view.findViewById(R.id.subjectsList)
        subjects.text = "Subjects : Math, Physics"

        //val bookAppointmentBtn = view.findViewById<Button>(R.id.bookAppointmentBtn1)
        val bookAppointmentBtn : Button = view.findViewById(R.id.appointmentBtn23)
        bookAppointmentBtn.setOnClickListener{
            val navController = Navigation.findNavController(view)
            val action = TeacherDetailsDirections.actionTeacherDetailsToBookAppointment()
            navController.navigate(action)
        }

        val feedbackBtn = view.findViewById<Button>(R.id.feedbackBtn)
        feedbackBtn.setOnClickListener{
            var bundle = Bundle()
            bundle.putString("teacherEmail",email.toString());

            val navController = Navigation.findNavController(view)
            navController.navigate(R.id.action_teacherDetails_to_addFeedback,bundle)
        }

//        val teacherDetailBackBtn : ImageButton = view.findViewById(R.id.teacherDetailBackBtn)
//
//        teacherDetailBackBtn.setOnClickListener{
//            val navController = Navigation.findNavController(view)
//            val action = TeacherDetailsDirections.actionTeacherDetailsToNavigationHomeFragment()
//            navController.navigate(action)
//        }

        return view
    }
}