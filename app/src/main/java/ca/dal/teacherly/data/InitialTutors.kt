package ca.dal.teacherly.data

import ca.dal.teacherly.models.Tutor
import ca.dal.teacherly.models.User

object InitialTutors {

    private var tutors: MutableList<Tutor> = mutableListOf(
        Tutor("Dr. Jane Doe", "2022-11-12", "2022-11-12", "+12037823831", "jane@gmail.com", User("1"), "25.50", "https://www.google.com"),
        Tutor("Dr. John Doe", "2022-11-12", "2022-11-12", "+12037823831", "jane@gmail.com", User("1"), "40.50", "https://www.google.com"),
    )

    fun addTutor(tutor:Tutor) {
        tutors.add(tutor)
    }

    fun getAll():List<Tutor> {
        return tutors.toList()
    }

}