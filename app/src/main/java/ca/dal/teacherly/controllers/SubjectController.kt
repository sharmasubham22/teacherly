package ca.dal.teacherly.controllers

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import ca.dal.teacherly.adapters.SubjectsAdapter
import ca.dal.teacherly.data.InitialSubjects
import ca.dal.teacherly.databinding.FragmentSubjectsBinding
import ca.dal.teacherly.models.Subject
import ca.dal.teacherly.models.Tutor
import ca.dal.teacherly.utils.Constants
import ca.dal.teacherly.utils.DatabaseSingleton
import com.google.android.gms.tasks.Task

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import java.time.Instant
import java.time.format.DateTimeFormatter

/*
 * @author Bharatwaaj Shankaranarayanan
 * @description Controller/Presenter for Subjects Module
 *              Mainly helps in modifying and maintaining the subjects module and updates the view
 */
class SubjectController {


    init {

    }

    companion object {

        // Method to initialize and synchronize from Firebase onto local
        fun initializeSubjectsFromFirebase(_binding : FragmentSubjectsBinding, ctx: Context?) {

            // Get Database Singleton reference
            var ref = DatabaseSingleton.getSubjectsReference().get();

            // Clear initial subjects data from local and clear the list of Subject Model reference
            InitialSubjects.clearAll();

            // Retreive all data from firebase using on success listener
            ref.addOnSuccessListener {

                // Get entire document size
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

                    _binding!!.subjectsList.adapter = SubjectsAdapter(InitialSubjects.getAll())
                    _binding!!.subjectsList.layoutManager = GridLayoutManager(ctx, 2, GridLayoutManager.VERTICAL, false)
                }
            }
        }

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