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

class AssignmentSubmit: AppCompatActivity() {
    lateinit var submit: Button
    lateinit var comments: EditText
    lateinit var submitTitle: TextView
    lateinit var subInstructions: TextView
    lateinit var subdue:TextView
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView((R.layout.submit_assignment))

        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        submit=findViewById(R.id.submit)
        comments=findViewById(R.id.comments)
        submitTitle=findViewById(R.id.submitTitleAssignment)
        subdue=findViewById(R.id.submitdue)
        subInstructions=findViewById(R.id.submitInstructions)

        val bundle:Bundle?=intent.extras

        val title = bundle!!.getString("Title")
        val due = bundle!!.getString("DueDate")
        val inst = bundle!!.getString("Instructions")

        submitTitle.text=title
        subdue.text=due
        subInstructions.text=inst

        submit.setOnClickListener {
            submitAssignment()
        }
    }
    private fun submitAssignment(){
        val comm=comments.text.toString().trim()
        val mapUpdate= mapOf(
            "submissionComments" to comm
        )
        db.collection("ASSIGNMENTS").document().update(mapUpdate)
            .addOnSuccessListener {
                Toast.makeText(this, "Successfull submitted",Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(this, it.toString(),Toast.LENGTH_SHORT).show()
            }
    }
}
