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

class TutorController {

    companion object {
        fun initializeTutors(_binding: FragmentHomeBinding, ctx : Context?) {
            var ref = DatabaseSingleton.getUserReference().get();

            InitialTutors.clearAll();

            ref.addOnSuccessListener {
                val size = it.documents.count()-1
                for (idx in 0..size){
                    if(it.documents.get(idx).get("Type")?.toString().toString() == "Teacher"){
                        var name = it.documents.get(idx).get("Name")?.toString().toString();
                        var hourlyRate = it.documents.get(idx).get("Hourly Rate")?.toString().toString()
                        var phone = it.documents.get(idx).get("Mobile Number")?.toString().toString();
                        var tutorImageURL = it.documents.get(idx).get("imageURL")?.toString().toString()
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