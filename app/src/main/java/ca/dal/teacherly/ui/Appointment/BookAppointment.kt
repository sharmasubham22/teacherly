package ca.dal.teacherly.ui.Appointment

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.navigation.Navigation
import ca.dal.teacherly.R
import ca.dal.teacherly.utils.DatabaseSingleton
import ca.dal.teacherly.utils.TeacherlyApplication.Companion
import com.google.firebase.Timestamp
import java.text.SimpleDateFormat
import java.util.*

/**
 * A simple [Fragment] subclass.
 * Use the [BookAppointment.newInstance] factory method to
 * create an instance of this fragment.
 */
class BookAppointment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_book_appointment, container, false)

        val appointmentDate = view.findViewById<EditText>(R.id.appointmentDate)
        appointmentDate.showSoftInputOnFocus = false;
        var date = ""
        appointmentDate.setOnClickListener {
            val calendarInstance = Calendar.getInstance()

            val year = calendarInstance.get(Calendar.YEAR)
            val month = calendarInstance.get(Calendar.MONTH)
            val day = calendarInstance.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog (requireActivity(),
                { view, year, monthOfYear, dayOfMonth ->

                    val dat = (monthOfYear + 1 ).toString() + "/" + dayOfMonth.toString()  + "/" + year
                    appointmentDate.setText(dat)
                    date = dat
                },
                year,
                month,
                day
            )

            datePickerDialog.datePicker.minDate = System.currentTimeMillis() - 1000

            datePickerDialog.show()
        }

        val bookAppointmentBtn1 = view.findViewById<Button>(R.id.bookAppointmentBtn1)
        bookAppointmentBtn1.setOnClickListener{
            val appointmentDescription = view.findViewById<EditText>(R.id.appointmentDescription).text.toString()

            var bookingMap = hashMapOf(
                "bookingDesc" to appointmentDescription,
                "tutorName" to arguments?.get("teacherName").toString(),
                "userEmail" to Companion.email,
                "teacherEmail" to arguments?.get("teacherEmail").toString(),
                "bookingDate" to Timestamp(Date(appointmentDate.text.toString())),
                "bookingSubject" to "Maths"
            )

            val booking = DatabaseSingleton.getBookingsReference()
            booking.document().set(bookingMap)
                .addOnSuccessListener {
                    Toast.makeText(context, "Successfull submitted", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener {
                    println(it.toString());
                    Toast.makeText(context, it.toString(), Toast.LENGTH_SHORT).show()
                }

            val navController = Navigation.findNavController(view)
            navController.navigate(R.id.action_bookAppointment_to_navigationHomeFragment)
        }

//        val imageButton5 : ImageButton = view.findViewById(R.id.bookAppointmentBackBtn)
//
//        imageButton5.setOnClickListener{
//            val navController = Navigation.findNavController(view)
//            val action = BookAppointmentDirections.actionBookAppointmentToTeacherDetails()
//            navController.navigate(action)
//        }

        return view
    }
}