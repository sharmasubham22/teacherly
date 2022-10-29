package ca.dal.teacherly.controllers

import ca.dal.teacherly.models.Location
import ca.dal.teacherly.models.Tutor

class SearchController {

    fun searchByLocation(location: Location): Array<Tutor>{
        return arrayOf<Tutor>()
    }

    fun searchByText(searchTerm: String): Array<Tutor>{
        return arrayOf<Tutor>()
    }
}