package ca.dal.teacherly.controllers

import android.content.Context
import androidx.recyclerview.widget.GridLayoutManager
import ca.dal.teacherly.adapters.TutorsAdapter
import ca.dal.teacherly.data.InitialTutors
import ca.dal.teacherly.databinding.FragmentHomeBinding
import ca.dal.teacherly.models.Tutor
import ca.dal.teacherly.utils.DatabaseSingleton
import java.time.Instant
import java.time.format.DateTimeFormatter

/*
 * @author Bharatwaaj Shankaranarayanan
 * @description Controller/Presenter for Tutors Module
 *              Mainly helps in modifying and maintaining the tutors module and updates the view
 */
class TutorController {

    // Empty constructor
    init {

    }

    // Static methods goes here
    companion object {

        // Method to initialize tutors
        fun initializeTutors(_binding: FragmentHomeBinding, ctx : Context?) {

            // Obtaining singleton database reference
            var ref = DatabaseSingleton.getUserReference().get();

            // Clear out all existing tutors
            InitialTutors.clearAll();

            // Add On Success Listener of Firestore DB
            ref.addOnSuccessListener {

                // Get entire document size
                val size = it.documents.count() - 1

                // Loop through all the available subjects
                for (idx in 0..size){

                    // Check if the user is of type Teacher
                    if(it.documents.get(idx).get("Type")?.toString().toString() == "Teacher"){

                        // Get the name of the teacher
                        var name = it.documents.get(idx).get("Name")?.toString().toString();

                        // Get hourly rate of the teacher
                        var hourlyRate = it.documents.get(idx).get("Hourly Rate")?.toString().toString()

                        // Get phone number of the teacher
                        var phone = it.documents.get(idx).get("Mobile Number")?.toString().toString();

                        // Get tutor image url from Firestore
                        var tutorImageURL = it.documents.get(idx).get("imageURL")?.toString().toString()

                        // Create and add tutor to the tutors list
                        InitialTutors.addTutor(
                            Tutor(name, DateTimeFormatter.ISO_INSTANT.format(Instant.now()), DateTimeFormatter.ISO_INSTANT.format(
                                Instant.now()), phone, it.documents.get(idx).id, hourlyRate, tutorImageURL)
                        );

                    }
                }
                _binding!!.homeTutorsList.adapter = TutorsAdapter(InitialTutors.getAll())
                _binding!!.homeTutorsList.layoutManager = GridLayoutManager(ctx, 2, GridLayoutManager.VERTICAL, false)
            }
        }
    }

    fun getAllTutors(): Array<Tutor>{
        return arrayOf<Tutor>()
    }

    fun getTutorById(id: String): Tutor {
        return Tutor()
    }

    fun getTutorByName(name: String): Tutor {
        return Tutor()
    }

    fun updateTutor(id: String, Tutor: Tutor): Boolean{
        return true
    }
}