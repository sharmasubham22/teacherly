package ca.dal.teacherly.ui.Student

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.RatingBar
import androidx.navigation.Navigation
import ca.dal.teacherly.R
import ca.dal.teacherly.utils.DatabaseSingleton
import android.widget.Toast
import ca.dal.teacherly.MainActivity

/**
 * A simple [Fragment] subclass.
 * Use the [AddFeedback.newInstance] factory method to
 * create an instance of this fragment.
 */
class AddFeedback : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_add_feedback, container, false)

        val submitFeedbackBtn = view.findViewById<Button>(R.id.submitFeedbackBtn)
        submitFeedbackBtn.setOnClickListener{
            val ratings = view.findViewById<RatingBar?>(R.id.ratingBar).rating
            println("Rating = ${ratings}")

            val feedbackDes = view.findViewById<EditText>(R.id.feedback).text.toString()
            println("Feedback = $feedbackDes")

            var feedbackMap = hashMapOf(
                "Rating" to ratings,
                "Description" to feedbackDes,
                "Teacher" to arguments?.get("teacherEmail"),
                "Student" to ""
            )

            val feedback = DatabaseSingleton.getFeedbackReference()
            feedback.document().set(feedbackMap)
                .addOnSuccessListener {
                    Toast.makeText(context, "Successfull submitted",Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener {
                    println(it.toString());
                    Toast.makeText(context, it.toString(),Toast.LENGTH_SHORT).show()
                }

            val navController = Navigation.findNavController(view)
            navController.navigate(R.id.action_addFeedback_to_navigationHomeFragment)
        }

//        val addFeedbackBackBtn : ImageButton = view.findViewById(R.id.addFeedbackBackBtn)
//        addFeedbackBackBtn.setOnClickListener{
//            val navController = Navigation.findNavController(view)
//            val action = AddFeedbackDirections.actionAddFeedbackToTeacherDetails()
//            navController.navigate(action)
//        }

        return view
    }
}