package ca.dal.teacherly.controllers

import ca.dal.teacherly.models.Student

class StudentController {

    fun getAllStudents(): Array<Student>{
        return arrayOf<Student>()
    }

    fun getStudentById(id: String): Student{
        return Student()
    }

    fun getStudentByName(name: String): Student{
        return Student()
    }

    fun updateStudent(id: String, student: Student): Boolean{
        return true
    }
}