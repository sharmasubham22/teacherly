package ca.dal.teacherly.ui.Teacher

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.Navigation
import ca.dal.teacherly.R
import com.squareup.picasso.Picasso

/*
 * @author Sarthak Patel
 * @description Teacher Detail Fragment where all details of the teacher is visible
 */

/*
 * Reference Taken From
 * https://learntodroid.com/how-to-move-between-fragments-using-the-navigation-component/
 * https://guides.codepath.com/android/Displaying-Images-with-the-Picasso-Library
 * https://firebase.google.com/docs/database/android/read-and-write#kotlin+ktx_5
 */
class TeacherDetails : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_teacher_details, container, false)

        val email = arguments?.get("teacherEmail")
        val textView : TextView = view.findViewById(R.id.TeacherName)
        textView.text = arguments?.get("teacherName").toString()

        //loading teacher image in the image view using Picasso
        val teacherImage: ImageView = view.findViewById(R.id.teacherProfilePic)
        Picasso.get()
            .load(arguments?.get("teacherImageURL").toString())
            .resize(64,64)
            .into(teacherImage);

        val rate : TextView = view.findViewById(R.id.rate)
        rate.text = arguments?.get("teacherCost").toString()+"$"

        val subjects : TextView = view.findViewById(R.id.subjectsList)
        subjects.text = "Subjects : Math, Physics"

        //setting up the onclick listener on book appointment button to redirect it to the bookAppointment fragment
        val bookAppointmentBtn : Button = view.findViewById(R.id.appointmentBtn23)
        bookAppointmentBtn.setOnClickListener{
            var bundle = Bundle()
            bundle.putString("teacherEmail",email.toString());
            bundle.putString("teacherName",arguments?.get("teacherName").toString())

            val navController = Navigation.findNavController(view)
            navController.navigate(R.id.action_teacherDetails_to_bookAppointment,bundle)
        }

        //setting up the onclick listener on add feedback button to redirect it to the addFeedback fragment
        val feedbackBtn = view.findViewById<Button>(R.id.feedbackBtn)
        feedbackBtn.setOnClickListener{
            var bundle = Bundle()
            bundle.putString("teacherEmail",email.toString());

            val navController = Navigation.findNavController(view)
            navController.navigate(R.id.action_teacherDetails_to_addFeedback,bundle)
        }

        return view
    }
}