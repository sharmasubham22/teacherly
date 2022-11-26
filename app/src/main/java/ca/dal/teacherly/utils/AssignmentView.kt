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

class AssignmentView : AppCompatActivity() {
    lateinit var assignList: MutableList<Assignments>
    lateinit var recyclerView: RecyclerView
    lateinit var assignmentList: ArrayList<Assignments>
    lateinit var aadapter: AssignmentAdapter

    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView((R.layout.teacher_assignment_layout))

        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()
        assignList = mutableListOf();
        recyclerView = findViewById(R.id.list_view)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)

        assignmentList = arrayListOf()

        aadapter = AssignmentAdapter(assignmentList)

        recyclerView.adapter = aadapter

        aadapter.setOnItemCickListener(object :AssignmentAdapter.onItemClickListener{
            override fun onItemClick(position: Int) {
//                Toast.makeText(this@AssignmentView,"You clicked on $position",Toast.LENGTH_SHORT).show()
                val intent= Intent(this@AssignmentView,AssignmentSubmit::class.java)
                intent.putExtra("Title", assignmentList[position].Title)
                intent.putExtra("DueDate",assignmentList[position].DueDate)
                intent.putExtra("Instructions", assignmentList[position].Instructions)
                startActivity(intent)
            }

        })

        EventChangeListener()
    }
    private fun EventChangeListener(){

        db.collection("ASSIGNMENTS")
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