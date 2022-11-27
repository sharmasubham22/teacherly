package ca.dal.teacherly.utils

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import ca.dal.teacherly.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class AssignmentGrade: AppCompatActivity() {
    lateinit var submit: Button
    lateinit var comments: TextView
    lateinit var grade: EditText
    lateinit var feedback: EditText
    lateinit var title: String
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView((R.layout.grade_assignment))

        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        submit=findViewById(R.id.gradebutton)
        comments=findViewById(R.id.givenComments)
        grade=findViewById(R.id.grade)
        feedback=findViewById(R.id.assignmentFeedback)

        //reference: https://www.youtube.com/watch?v=EoJX7h7lGxM&t=466s
        val bundle: Bundle?=intent.extras
        val givencomments = bundle!!.getString("submissionComments")
        title = bundle!!.getString("Title").toString();
        comments.text=givencomments

        submit.setOnClickListener {
            gradeAssignment()
        }
    }

    //to store the grade and feedback for the assignment
    //reference: https://www.youtube.com/watch?v=aePJ-Zc4ZX8
    private fun gradeAssignment(){

        val gr=grade.text.toString().trim()
        val feed=feedback.text.toString().trim()
        val mapUpdate= mapOf(
            "Grade" to gr,
        "Feedback" to feed
        )
        DatabaseSingleton.getAssignmentReference().document(title).update(mapUpdate)
            .addOnSuccessListener {
                Toast.makeText(this, "Successfully graded", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(this, it.toString(), Toast.LENGTH_SHORT).show()
            }
    }
}