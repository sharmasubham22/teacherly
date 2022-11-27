package ca.dal.teacherly.utils

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ListView
import android.widget.TextView
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

class AssignmentView : AppCompatActivity() {
    lateinit var assignList: MutableList<Assignments>
    lateinit var recyclerView: RecyclerView
    lateinit var assignmentList: ArrayList<Assignments>
    lateinit var aadapter: AssignmentAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView((R.layout.teacher_assignment_layout))

        assignList = mutableListOf();
        recyclerView = findViewById(R.id.list_view)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)
        assignmentList = arrayListOf()
        aadapter = AssignmentAdapter(assignmentList)
        recyclerView.adapter = aadapter

        //on item click listener sends data to next screen
        //reference: https://www.youtube.com/watch?v=EoJX7h7lGxM&t=465s
        aadapter.setOnItemCickListener(object :AssignmentAdapter.onItemClickListener{
            override fun onItemClick(position: Int) {
                val intent= Intent(this@AssignmentView,AssignmentSubmit::class.java)
                intent.putExtra("Title", assignmentList[position].Title)
                intent.putExtra("DueDate",assignmentList[position].DueDate)
                intent.putExtra("Instructions", assignmentList[position].Instructions)
                intent.putExtra("Grade",assignmentList[position].Grade)
                startActivity(intent)
                var g=findViewById<TextView>(R.id.textViewGrade)
                if (g.length()!=0){
                    Toast.makeText(this@AssignmentView,"You have already submitted",Toast.LENGTH_SHORT).show()
                }
            }
        })
        EventChangeListener()
    }

    //view of assignments list on te screen from firestore database
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