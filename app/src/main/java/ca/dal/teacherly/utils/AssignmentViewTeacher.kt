package ca.dal.teacherly.utils

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ca.dal.teacherly.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.QuerySnapshot
import com.google.protobuf.Value

class AssignmentViewTeacher : AppCompatActivity() {
    lateinit var assignList: MutableList<Assignments>
    lateinit var recyclerView: RecyclerView
    lateinit var assignmentList: ArrayList<Assignments>
    lateinit var aadapter: AssignmentAdapterTeacher

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView((R.layout.assignment_view))

        assignList = mutableListOf();
        recyclerView = findViewById(R.id.list_view2)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)
        assignmentList = arrayListOf()
        aadapter = AssignmentAdapterTeacher(assignmentList)
        recyclerView.adapter = aadapter

        //on item click listener sends data to next screen
        //reference: https://www.youtube.com/watch?v=EoJX7h7lGxM&t=465s
        aadapter.setOnItemCickListener(object :AssignmentAdapterTeacher.onItemClickListener{
            override fun onItemClick(position: Int) {
//                Toast.makeText(this@AssignmentView,"You clicked on $position",Toast.LENGTH_SHORT).show()
                val intent= Intent(this@AssignmentViewTeacher,AssignmentGrade::class.java)
                intent.putExtra("Title", assignmentList[position].Title)
                intent.putExtra("DueDate",assignmentList[position].DueDate)
                intent.putExtra("submissionComments", assignmentList[position].submissionComments)
                startActivity(intent)
            }
        })
        EventChangeListener()
    }

    //view of assignments submissions list on te screen from firestore database
    private fun EventChangeListener(){
        DatabaseSingleton.getAssignmentReference()
            .addSnapshotListener(object: EventListener<QuerySnapshot>{
                override fun onEvent(value: QuerySnapshot?, error: FirebaseFirestoreException?) {
                    if(error!=null){
                        Log.e("Firestore error", error.message.toString())
                        return
                    }
                    for (dc:DocumentChange in value?.documentChanges!!){
                        if(dc.type==DocumentChange.Type.ADDED){
                            assignmentList.add(dc.document.toObject(Assignments::class.java))
                        }
                    }
                    aadapter.notifyDataSetChanged()
                }
            })
    }
}