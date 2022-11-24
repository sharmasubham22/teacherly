package ca.dal.teacherly.models

data class Tutor(
    var tutorName: String = "",
    var createdAt: String = "",
    var updatedAt: String = "",
    var mobileNumber: String = "",
    var email: String = "",
    var costPerHour: String = "",
    var tutorImageURL: String = "",
    var city: String = "",
    var latitude: Double = 0.0,
    var longitude: Double = 0.0,
    var postalCode: String = "",
    var province: String = "",
    var streetName: String = ""
)