package ca.dal.teacherly.data

import ca.dal.teacherly.models.Subject
import ca.dal.teacherly.models.Tutor
import ca.dal.teacherly.models.User

object InitialSubjects {

    private var subjects: MutableList<Subject> = mutableListOf(
        Subject("Mathematics", "2022-11-12", "2022-11-12", "https://www.google.com"),
        Subject("Science", "2022-11-12", "2022-11-12",  "https://www.google.com"),
        Subject("Computer Science", "2022-11-12", "2022-11-12",  "https://www.google.com"),
        Subject("Engineering", "2022-11-12", "2022-11-12",  "https://www.google.com"),
        Subject("Mobile Computing", "2022-11-12", "2022-11-12",  "https://www.google.com"),
    )

    fun addTutor(subject:Subject) {
        subjects.add(subject)
    }

    fun getAll():List<Subject> {
        return subjects.toList()
    }

}