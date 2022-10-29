package ca.dal.teacherly.controllers

import ca.dal.teacherly.models.Tutor
import javax.security.auth.Subject

class SubjectController {

    fun createSubject(subject: Subject) : Boolean{
        return true
    }

    fun updateSubject(subjectId: String, subject: Subject): Boolean{
        return true
    }

    fun mapSubjectToTutor(subject: Subject, tutor: Tutor):Boolean{
        return true
    }
}