package ca.dal.teacherly.models

import com.google.firebase.firestore.QuerySnapshot

class LocationModel {

    fun getTeacherList(documents: QuerySnapshot): ArrayList<Tutor> {
        val teacherList: ArrayList<Tutor> = arrayListOf()

        for (document in documents) {
            val t = Tutor()
            document?.data?.let { data ->
                data.forEach { (k, v) ->
                    when (k) {
                        "City" -> {
                            t.city = v as String
                        }
                        "Email" -> {
                            t.email = v as String
                        }
                        "Latitude" -> {
                            t.latitude = v as Double
                        }
                        "Longitude" -> {
                            t.longitude = v as Double
                        }
                        "Mobile Number" -> {
                            t.mobileNumber = v as String
                        }
                        "Name" -> {
                            t.tutorName = v as String
                        }
                        "Postal Code" -> {
                            t.postalCode = v as String
                        }
                        "Province" -> {
                            t.province = v as String
                        }
                        "Street Name" -> {
                            t.streetName = v as String
                        }
                    }
                }

                teacherList!!.add(t)

            }
        }
        return teacherList
    }
}