package ca.dal.teacherly.controllers

import android.widget.Toast
import ca.dal.teacherly.models.Subject
import ca.dal.teacherly.models.Tutor
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

    fun getAllSubjects() : ArrayList<Subject> {

        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        var ref = db.collection("SUBJECTS").get();
        val subjects: ArrayList<Subject> = ArrayList();



        ref.addOnSuccessListener {

            for (idx in 0..it.documents.count()){
                var subjectName = it.documents.get(idx).get("name")?.toString().toString();
//                var subjectImageURL = it.documents.get(idx).get("imageURL")?.toString().toString()
                var subjectImageURL = "https://google.com";
                print("Subject Name" + subjectName)
                subjects.add(Subject(subjectName, DateTimeFormatter.ISO_INSTANT.format(Instant.now()), DateTimeFormatter.ISO_INSTANT.format(Instant.now()), subjectImageURL));
            }

        }

        return subjects
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