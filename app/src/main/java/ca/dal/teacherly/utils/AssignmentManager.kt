package ca.dal.teacherly.utils

import android.app.Activity
import android.app.DatePickerDialog
import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import ca.dal.teacherly.R
import com.google.firebase.storage.FirebaseStorage
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap

class AssignmentManager: AppCompatActivity() {
    lateinit var assigntitle: EditText
    lateinit var publishOn: EditText
    lateinit var due: EditText
    lateinit var inst: EditText
    lateinit var create: Button
    lateinit var filepath: Uri
    lateinit var upload: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView((R.layout.create_assignment))

        assigntitle = findViewById((R.id.titleAssignment))
        publishOn = findViewById(R.id.publishDate)
        due = findViewById(R.id.dueDate)
        inst = findViewById(R.id.instructions)
        create = findViewById(R.id.saveAssignment)
        upload = findViewById(R.id.file)

        create.setOnClickListener {
            saveAssignment()
        }
        publishOn.setOnClickListener {
            selectPublishDate()
        }
        due.setOnClickListener {
            selectDueDate()
        }
        upload.setOnClickListener {
            startFileChooser()
        }
    }

    //datepicker for due date
    //reference: https://www.youtube.com/watch?v=w038N6FWYOc
    private fun selectDueDate(){
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        val dpd=
            DatePickerDialog(this, DatePickerDialog.OnDateSetListener{ view: DatePicker, mYear:Int, mMonth:Int, mDay:Int ->
            due.setText(""+mMonth+"/"+mDay+"/"+mYear)

        }, year, month, day)
        dpd.show()
    }

    //date picker for publish date
    //reference: https://www.youtube.com/watch?v=w038N6FWYOc
    private fun selectPublishDate(){
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        val dpd=
            DatePickerDialog(this, DatePickerDialog.OnDateSetListener{ view: DatePicker, mYear:Int, mMonth:Int, mDay:Int ->
                publishOn.setText(""+mMonth+"/"+mDay+"/"+mYear)

            }, year, month, day)
        dpd.show()
    }

    //to choose a file from the device
    //reference: https://www.youtube.com/watch?v=j11O6DvkePg
    private fun startFileChooser(){
        var i = Intent()
        i.setType("application/pdf")
        i.setAction(Intent.ACTION_GET_CONTENT)
        startActivityForResult(Intent.createChooser(i, "Choose a file to Upload"), 111)
    }

    //to open file path and show the file chosen
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val uriTxt = findViewById(R.id.uriText) as TextView
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode==111 && resultCode== Activity.RESULT_OK && data != null){
            filepath=data.data!!
            uriTxt.text = filepath.toString()
        }
    }

    //to create a new assignment with file attachment feature
    /*reference: https://www.youtube.com/watch?v=5UEdyUFi_uQ
    https://www.youtube.com/watch?v=j11O6DvkePg */
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
            if(filepath!=null){
            var pd= ProgressDialog(this);
            pd.setTitle("Uploading")
            pd.show()
                val formatter= SimpleDateFormat("yyyy_MM_dd_HH:mm:ss", Locale.getDefault())
                val now=Date()
                val fileName=formatter.format(now)
            var pdfref= FirebaseStorage.getInstance().getReference("assignments/$fileName")
            pdfref.putFile(filepath)
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

        val assignment:MutableMap<String, Any> = HashMap()

        assignment["Title"]=title
        assignment["PublishDate"]=pubDate
        assignment["DueDate"]=dues
        assignment["Instructions"]=assigninstr

        DatabaseSingleton.getAssignmentReference().document(title)
            .set(assignment)
            .addOnSuccessListener {
                Toast.makeText(this,"Assignment Saved Successfuly", Toast.LENGTH_LONG).show()
            }
            .addOnFailureListener{
                Toast.makeText(this,"Assignment Creation failed", Toast.LENGTH_LONG).show()
        }

    }
}
