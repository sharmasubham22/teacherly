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

        val bundle: Bundle?=intent.extras
        val givencomments = bundle!!.getString("submissionComments")

        comments.text=givencomments

        submit.setOnClickListener {
            gradeAssignment()
        }
    }
    private fun gradeAssignment(){
        val gr=grade.text.toString().trim()
        val feed=feedback.text.toString().trim()
        val mapUpdate= mapOf(
            "Grade" to gr,
        "Feedback" to feed
        )
        db.collection("ASSIGNMENTS").document("Assignment 1").update(mapUpdate)
            .addOnSuccessListener {
                Toast.makeText(this, "Successfully graded", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(this, it.toString(), Toast.LENGTH_SHORT).show()
            }
    }
}