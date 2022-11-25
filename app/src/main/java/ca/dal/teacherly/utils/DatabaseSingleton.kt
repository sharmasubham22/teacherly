package ca.dal.teacherly.utils

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

object DatabaseSingleton {

    private val database = Firebase.firestore
    private val usersReference = database.collection(Constants.FB_USERS_SCHEMA)
    private val subjectsReference = database.collection(Constants.FB_SUBJECTS_SCHEMA)
    private val assignmentsReference = database.collection("ASSIGNMENTS")
    private val bookingReference = database.collection(Constants.FB_BOOKINGS_SCHEMA)

    fun getUserReference(): CollectionReference {
        return usersReference
    }

    fun getSubjectsReference(): CollectionReference {
        return subjectsReference
    }

    fun getAssignmentReference(): CollectionReference {
        return assignmentsReference
    }

    fun getBookingsReference(): CollectionReference {
        return bookingReference
    }
}