package ca.dal.teacherly.controllers

import ca.dal.teacherly.models.LocationModel
import ca.dal.teacherly.models.Tutor

class SearchController {

    fun searchByLocation(location: LocationModel): Array<Tutor>{
        return arrayOf<Tutor>()
    }

    fun searchByText(searchTerm: String): Array<Tutor>{
        return arrayOf<Tutor>()
    }
}