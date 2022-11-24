package ca.dal.teacherly.utils

import java.sql.Timestamp

data class Assignments(val Title:String, val PublishDate: String, val DueDate: String, val Instructions:String){
    constructor() : this("","","","") {

    }
}