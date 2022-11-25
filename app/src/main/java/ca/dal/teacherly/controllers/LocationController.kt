package ca.dal.teacherly.controllers

import android.location.Location
import ca.dal.teacherly.models.Student
import ca.dal.teacherly.models.Tutor

class LocationController {

    fun addLocation(location: Location): Boolean {
        return true
    }

    fun mapLocationToTutor(location: Location, tutor: Tutor): Boolean {
        return true
    }

    fun mapLocationToStudent(location: Location, student: Student): Boolean {
        return true
    }

    fun updateLocation(locationId: String, location: Location): Boolean {
        return true
    }

}