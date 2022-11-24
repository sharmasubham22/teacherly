package ca.dal.teacherly.models

import java.util.*

data class Sessions(
    val bookingId: String,
    val bookingDate: String,
    val description: String,
    val subject: String,
    val tutorName: String
);