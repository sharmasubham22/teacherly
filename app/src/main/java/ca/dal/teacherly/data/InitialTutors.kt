package ca.dal.teacherly.data

import ca.dal.teacherly.models.Tutor
import ca.dal.teacherly.models.User

object InitialTutors {

    private var tutors: MutableList<Tutor> = mutableListOf()

    fun addTutor(tutor:Tutor) {
        tutors.add(tutor)
    }

    fun getAll():List<Tutor> {
        return tutors.toList()
    }

    fun clearAll() {
        tutors.clear()
    }

}