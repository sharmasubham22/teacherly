package ca.dal.teacherly.utils

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore

object DatabaseSingleton {

    private val database = FirebaseFirestore.getInstance()
    private val usersReference = database.collection("USERS")
    private val subjectsReference = database.collection("SUBJECTS")
    private val coursesReference = database.collection("COURSES")
    private val assignmentsReference = database.collection("ASSIGNMENTS")

    fun getUserReference(): CollectionReference {
        return usersReference
    }

    fun getSubjectsReference(): CollectionReference {
        return subjectsReference
    }

    fun getCourseReference(): CollectionReference {
        return coursesReference
    }

    fun getAssignmentReference(): CollectionReference {
        return assignmentsReference
    }
}