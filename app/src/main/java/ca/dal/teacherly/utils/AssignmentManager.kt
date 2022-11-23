package ca.dal.teacherly.utils

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import ca.dal.teacherly.R
import ca.dal.teacherly.models.Assignments
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore

class AssignmentManager: AppCompatActivity() {
    lateinit var assigntitle: EditText
    lateinit var publishOn: EditText
    lateinit var due: EditText
    lateinit var inst: EditText

    lateinit var create: Button

    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView((R.layout.create_assignment))

        assigntitle = findViewById((R.id.titleAssignment))
        publishOn = findViewById(R.id.publishDate)
        due = findViewById(R.id.dueDate)
        inst = findViewById(R.id.instructions)
        create = findViewById(R.id.saveAssignment)

        create.setOnClickListener {
            saveAssignment()
        }
    }

    private fun saveAssignment() {
        val title = assigntitle.text.toString().trim()
        val pubDate = publishOn.text.toString().trim()
        val dues = due.text.toString().trim()
        val assigninstr = inst.text.toString().trim()

        if (title.isEmpty()) {
            assigntitle.error = "Please enter the title for the assignment"
            return
        }
        if (pubDate.isEmpty()) {
            publishOn.error = "Please enter the Publish date for the assignment"
            return
        }
        if (dues.isEmpty()) {
            due.error = "Please enter the due date for the assignment"
            return
        }
        if (assigninstr.isEmpty()) {
            inst.error = "Please enter the instructions for the assignment"
            return
        }
        val assignment:MutableMap<String, Any> = HashMap()
        assignment["id"]
        val assigns = db.collection("Assignments")
        assigns.add(assignment)
//        val assignment_id=ref.push().key
//        val assign = Assignments(assignment_id, title, pubDate, dues, assigninstr)
//
//        ref.child(assignment_id.toString()).setValue(assign).addOnCompleteListener{
//            Toast.makeText(applicationContext,"Assignment Saved Successfuly", Toast.LENGTH_LONG).show()
//        }
    }
}
