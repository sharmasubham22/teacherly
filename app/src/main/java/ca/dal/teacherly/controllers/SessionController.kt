package ca.dal.teacherly.controllers

import android.content.Context
import android.se.omapi.Session
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ca.dal.teacherly.adapters.SessionsAdapter
import ca.dal.teacherly.data.SessionsList
import ca.dal.teacherly.models.Sessions
import ca.dal.teacherly.models.Student
import ca.dal.teacherly.utils.DatabaseSingleton
import ca.dal.teacherly.utils.Utils
import com.google.firebase.Timestamp

/*
 * @author Bharatwaaj Shankaranarayanan
 * @description Controller/Presenter for Sessions Module
 *              Mainly helps in modifying and maintaining the Sessions module and updates the view
 */
class SessionController {

    // Static data points and methods
    companion object {

        // Method to initialize sessions
        fun initializeSessions(ctx : Context?, PastSessionsRV : RecyclerView, userEmail : String) {

            // Avail the Singleton DB Reference from the Common Firestore DB Class
            var ref = DatabaseSingleton.getBookingsReference().get();

            // Clear Initial Sessions Data If Any
            SessionsList.clearAll();

            // Add on Success Listener on the Firestore DB
            ref.addOnSuccessListener {

                // Get the size of sessions documents from Firestore
                val size = it.documents.count() - 1

                // Loop through all the documents
                for (idx in 0..size) {

                    // Fetch ID of the booking
                    var bookingUserEmail = it.documents.get(idx).get("userEmail")?.toString().toString()

                    // Check if the user logged in email and booking email matches
                    if(bookingUserEmail == userEmail) {

                        // Fetch ID of the booking
                        var bookingId = it.documents.get(idx).id.toString()

                        // Fetch Booking Description
                        var bookingDescription =
                            it.documents.get(idx).get("bookingDesc")?.toString().toString()

                        // Fetch booking Data Timestamp
                        var bookingDate: Timestamp =
                            it.documents.get(idx).get("bookingDate") as Timestamp

                        // Fetch booking Subject String
                        var bookingSubject =
                            it.documents.get(idx).get("bookingSubject")?.toString().toString()

                        // Fetch tutor name
                        var tutorName =
                            it.documents.get(idx).get("tutorName")?.toString().toString()

                        // Create and add a new session to the list
                        SessionsList.addSession(
                            Sessions(
                                bookingId,
                                Utils.getDateTime(bookingDate),
                                bookingDescription,
                                bookingSubject,
                                tutorName
                            )
                        );
                    }

                }

                // Update the view
                PastSessionsRV.adapter = SessionsAdapter(SessionsList.getAll())
                PastSessionsRV.layoutManager = LinearLayoutManager(ctx)
            }

        }

    }

    // Method to get all sessions
    fun getAllSessions(): Array<Session>{
        return arrayOf<Session>()
    }

    // Method to get upcoming sessions for student
    fun getUpcomingSessionsForStudent(student: Student): Array<Session>{
        return arrayOf<Session>()
    }

    // Method to get historical sessions for student
    fun getHistoricalSessionsForStudent(student: Student):Array<Session>{
        return arrayOf<Session>()
    }

    // Method to book a session for a student
    fun bookSession(session: Session): Boolean{
        return true
    }

    // Method to update a session
    fun updateSession(sessionId: String, session: Session): Boolean{
        return true
    }
}