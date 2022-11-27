package ca.dal.teacherly.utils

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import ca.dal.teacherly.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import java.lang.reflect.Array.get
import java.text.SimpleDateFormat
import java.util.*

class AssignmentSubmit: AppCompatActivity() {
    lateinit var submit: Button
    lateinit var comments: EditText
    lateinit var submitTitle: TextView
    lateinit var subInstructions: TextView
    lateinit var subdue:TextView
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    private lateinit var title: String;
    lateinit var filepath2: Uri
    lateinit var upload2: Button

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
        upload2 = findViewById(R.id.fileButton)

        val bundle:Bundle?=intent.extras

        title = bundle!!.getString("Title").toString();
        val due = bundle!!.getString("DueDate")
        val inst = bundle!!.getString("Instructions")

        submitTitle.text=title
        subdue.text=due
        subInstructions.text=inst

        submit.setOnClickListener {
            submitAssignment()
        }
        upload2.setOnClickListener {
            startFileChooser()
        }
    }
    private fun startFileChooser(){
        var i = Intent()
        i.setType("application/pdf")
        i.setAction(Intent.ACTION_GET_CONTENT)
        startActivityForResult(Intent.createChooser(i, "Choose a file to Upload"), 111)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val uriTxt = findViewById(R.id.filename) as TextView
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode==111 && resultCode== Activity.RESULT_OK && data != null){
            filepath2=data.data!!
            uriTxt.text = filepath2.toString()
        }
    }
    private fun submitAssignment(){
        val comm=comments.text.toString().trim()
        val ref=db.collection("ASSIGNMENTS")
//            ref.get()
//                .addOnSuccessListener {
//                    title
//                }

        if(filepath2!=null){
            var pd= ProgressDialog(this);
            pd.setTitle("Uploading")
            pd.show()
            val formatter=SimpleDateFormat("yyyy_MM_dd_HH:mm:ss", Locale.getDefault())
            val now=Date()
            val fileName=formatter.format(now)
            var pdfref= FirebaseStorage.getInstance().getReference("assignmentssubmissions/$fileName")
            pdfref.putFile(filepath2)
                .addOnSuccessListener {
                    pd.dismiss()
                }
                .addOnFailureListener{snapshot->
                    pd.dismiss()
                    Toast.makeText(this, snapshot.message, Toast.LENGTH_LONG).show()
                }
                .addOnProgressListener { p0->
                    var progress:Double =(100.0*p0.bytesTransferred)/p0.totalByteCount
                    pd.setMessage("Uploaded ${progress.toInt()}%")
                }
        }
        val mapUpdate= mapOf(
            "submissionComments" to comm
        )
        ref.document(title).update(mapUpdate)
            .addOnSuccessListener {
                Toast.makeText(this, "Successfully submitted",Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {

                Toast.makeText(this, it.toString(),Toast.LENGTH_SHORT).show()
            }
    }
}
