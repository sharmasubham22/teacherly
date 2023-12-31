package ca.dal.teacherly.controllers

import android.content.Context
import androidx.recyclerview.widget.GridLayoutManager
import ca.dal.teacherly.adapters.SubjectsAdapter
import ca.dal.teacherly.data.SubjectsList
import ca.dal.teacherly.databinding.FragmentSubjectsBinding
import ca.dal.teacherly.models.Subject
import ca.dal.teacherly.models.Tutor
import ca.dal.teacherly.utils.Constants
import ca.dal.teacherly.utils.DatabaseSingleton

import java.time.Instant
import java.time.format.DateTimeFormatter

/*
 * @author Bharatwaaj Shankaranarayanan
 * @description Controller/Presenter for Subjects Module
 *              Mainly helps in modifying and maintaining the subjects module and updates the view
 */
class SubjectController {

    // Empty constructor
    init {

    }

    // Static methods goes here
    companion object {

        // Method to initialize and synchronize from Firebase onto local
        fun initializeSubjectsFromFirebase(_binding : FragmentSubjectsBinding, ctx: Context?) {

            // Get Database Singleton reference
            var ref = DatabaseSingleton.getSubjectsReference().get();

            // Clear initial subjects data from local and clear the list of Subject Model reference
            SubjectsList.clearAll();

            // Retreive all data from firebase using on success listener
            ref.addOnSuccessListener {

                // Get entire document size
                val size = it.documents.count() - 1

                // Loop through all the available subjects
                for (idx in 0..size) {

                    // Retrieve subject name from Firestore
                    var subjectName = it.documents.get(idx).get(Constants.FB_SUBJECTS_SCHEMA_NAME_FIELD)?.toString().toString();

                    // Retrieve subject image URL from Firestore
                    var subjectImageURL =
                        it.documents.get(idx).get(Constants.FB_SUBJECTS_SCHEMA_IMAGE_URL_FIELD)?.toString().toString()

                    // Add the retrieved object into the subjects list
                    SubjectsList.addTutor(
                        Subject(
                            subjectName,
                            DateTimeFormatter.ISO_INSTANT.format(Instant.now()),
                            DateTimeFormatter.ISO_INSTANT.format(
                                Instant.now()
                            ),
                            subjectImageURL
                        )
                    );

                    // Update the view
                    _binding!!.subjectsList.adapter = SubjectsAdapter(SubjectsList.getAll())
                    _binding!!.subjectsList.layoutManager = GridLayoutManager(ctx, 2, GridLayoutManager.VERTICAL, false)
                }
            }
        }

    }

    // Method to create a new subject
    fun createSubject(subject: Subject) : Boolean{
        return true
    }

    // Method to update an existing subject
    fun updateSubject(subjectId: String, subject: Subject): Boolean{
        return true
    }

    // Method to map a subject to a tutor
    fun mapSubjectToTutor(subject: Subject, tutor: Tutor):Boolean{
        return true
    }
}