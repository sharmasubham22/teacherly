package ca.dal.teacherly.utils

import java.sql.Timestamp

class Assignments(val Title:String, val publishDate: String, val dueDate: String, val Instructions:String){
    constructor() : this("","","","") {

    }
}