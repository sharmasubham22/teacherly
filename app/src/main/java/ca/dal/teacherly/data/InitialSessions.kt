package ca.dal.teacherly.data

import ca.dal.teacherly.models.Sessions
import ca.dal.teacherly.models.Subject
import ca.dal.teacherly.models.Tutor
import ca.dal.teacherly.models.User

object InitialSessions {

    private var sessions: MutableList<Sessions> = mutableListOf()

    fun addTutor(session:Sessions) {
        sessions.add(session)
    }

    fun getAll():List<Sessions> {
        return sessions.toList()
    }

    fun clearAll() {
        sessions.clear();
    }

}