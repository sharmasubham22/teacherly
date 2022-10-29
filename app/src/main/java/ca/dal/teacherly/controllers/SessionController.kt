package ca.dal.teacherly.controllers

import android.se.omapi.Session
import ca.dal.teacherly.models.Student

class SessionController {

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