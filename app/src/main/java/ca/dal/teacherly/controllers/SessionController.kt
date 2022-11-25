package ca.dal.teacherly.controllers

import android.content.Context
import android.se.omapi.Session
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ca.dal.teacherly.adapters.SessionsAdapter
import ca.dal.teacherly.data.InitialSessions
import ca.dal.teacherly.models.Sessions
import ca.dal.teacherly.models.Student
import ca.dal.teacherly.utils.Constants
import ca.dal.teacherly.utils.DatabaseSingleton
import ca.dal.teacherly.utils.Utils
import com.google.firebase.Timestamp
import kotlinx.android.synthetic.main.activity_past_sessions.*

class SessionController {

    companion object {
        fun initializeSessions(ctx : Context?, PastSessionsRV : RecyclerView) {

            var ref = DatabaseSingleton.getBookingsReference().get();

            InitialSessions.clearAll();

            ref.addOnSuccessListener {
                val size = it.documents.count() - 1
                for (idx in 0..size) {

                    var bookingId = it.documents.get(idx).id.toString()
                    var bookingDescription = it.documents.get(idx).get("bookingDesc")?.toString().toString()
                    var bookingDate: Timestamp = it.documents.get(idx).get("bookingDate") as Timestamp
                    var bookingSubject = it.documents.get(idx).get("bookingSubject")?.toString().toString()
                    var tutorName = it.documents.get(idx).get("tutorName")?.toString().toString()

                    InitialSessions.addSession(
                        Sessions(
                            bookingId,
                            Utils.getDateTime(bookingDate),
                            bookingDescription,
                            bookingSubject,
                            tutorName
                        )
                    );

                }

                PastSessionsRV.adapter = SessionsAdapter(InitialSessions.getAll())
                PastSessionsRV.layoutManager = LinearLayoutManager(ctx)
            }

        }

    }

    fun getAllSessions(): Array<Session>{
        return arrayOf<Session>()
    }

    fun getUpcomingSessionsForStudent(student: Student): Array<Session>{
        return arrayOf<Session>()
    }

    fun getHistoricalSessionsForStudent(student: Student):Array<Session>{
        return arrayOf<Session>()
    }

    fun bookSession(session: Session): Boolean{
        return true
    }

    fun updateSession(sessionId: String, session: Session): Boolean{
        return true
    }
}