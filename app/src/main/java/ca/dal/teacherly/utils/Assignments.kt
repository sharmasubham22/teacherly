package ca.dal.teacherly.utils

data class Assignments(val Title:String, val PublishDate: String, val DueDate: String, val Instructions:String, val submissionComments:String, val Grade:String, val Feedback: String){
    constructor() : this("","","","","","","") {

    }
}