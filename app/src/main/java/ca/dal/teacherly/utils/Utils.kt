package ca.dal.teacherly.utils

import android.util.Log
import com.google.firebase.Timestamp
import java.text.SimpleDateFormat
import java.util.*

class Utils {

    companion object {
        fun getDateTime(timestamp: Timestamp): String {
            try {
                val milliseconds = timestamp.seconds * 1000 + timestamp.nanoseconds / 1000000
                val sdf = SimpleDateFormat("MM/dd/yyyy hh:mm")
                val netDate = Date(milliseconds)
                return sdf.format(netDate).toString()
            } catch (e: Exception) {
                return e.toString()
            }
        }
    }
}