package ca.dal.teacherly.utils

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

object DatabaseSingleton {

    private val database = Firebase.database
    private val usersReference = database.getReference("USERS")
    private val subjectsReference = database.getReference("SUBJECTS")
    private val coursesReference = database.getReference("COURSES")
    private val assignmentsReference = database.getReference("ASSIGNMENTS")

    fun getUserReference(): DatabaseReference {
        return usersReference
    }

    fun getSubjectsReference(): DatabaseReference {
        return subjectsReference
    }

    fun getCourseReference(): DatabaseReference {
        return coursesReference
    }

    fun getAssignmentReference(): DatabaseReference {
        return assignmentsReference
    }
}