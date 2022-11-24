package ca.dal.teacherly.controllers

import android.util.Log
import android.widget.Toast
import ca.dal.teacherly.data.InitialSubjects
import ca.dal.teacherly.models.Subject
import ca.dal.teacherly.models.Tutor
import ca.dal.teacherly.utils.Constants
import com.google.android.gms.tasks.Task

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import java.time.Instant
import java.time.format.DateTimeFormatter

class SubjectController {

    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    init {

    }

    companion object {

        fun initializeSubjectsFromFirebase(auth: FirebaseAuth, db : FirebaseFirestore) {
            var ref = db.collection(Constants.FB_SUBJECTS_SCHEMA).get();
            InitialSubjects.clearAll();
            ref.addOnSuccessListener {
                val size = it.documents.count() - 1
                for (idx in 0..size) {
                    var subjectName = it.documents.get(idx).get("name")?.toString().toString();
                    var subjectImageURL =
                        it.documents.get(idx).get("imageURL")?.toString().toString()
                    InitialSubjects.addTutor(
                        Subject(
                            subjectName,
                            DateTimeFormatter.ISO_INSTANT.format(Instant.now()),
                            DateTimeFormatter.ISO_INSTANT.format(
                                Instant.now()
                            ),
                            subjectImageURL
                        )
                    );
                }
            }
        }

    }

    fun getAllSubjects() : ArrayList<Subject> {

        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        var ref = db.collection("SUBJECTS").get();
        val subjects: ArrayList<Subject> = ArrayList();



        ref.addOnSuccessListener {

            val size = it.documents.count()-1

            for (idx in 0..size){
                var subjectName = it.documents.get(idx).get("name")?.toString().toString();
//                var subjectImageURL = it.documents.get(idx).get("imageURL")?.toString().toString()
                var subjectImageURL = "https://google.com";
                Log.d("Subject Name", subjectName)
                subjects.add(Subject(subjectName, DateTimeFormatter.ISO_INSTANT.format(Instant.now()), DateTimeFormatter.ISO_INSTANT.format(Instant.now()), subjectImageURL));
            }

        }

        return subjects;
    }

    fun createSubject(subject: Subject) : Boolean{
        return true
    }

    fun updateSubject(subjectId: String, subject: Subject): Boolean{
        return true
    }

    fun mapSubjectToTutor(subject: Subject, tutor: Tutor):Boolean{
        return true
    }
}