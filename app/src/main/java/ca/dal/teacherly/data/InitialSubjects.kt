package ca.dal.teacherly.data

import ca.dal.teacherly.models.Subject
import ca.dal.teacherly.models.Tutor
import ca.dal.teacherly.models.User

object InitialSubjects {

    private var subjects: MutableList<Subject> = mutableListOf()

    fun addTutor(subject:Subject) {
        subjects.add(subject)
    }

    fun getAll():List<Subject> {
        return subjects.toList()
    }

    fun clearAll() {
        subjects.clear();
    }

}