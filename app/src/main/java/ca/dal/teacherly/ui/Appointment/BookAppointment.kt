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
import java.util.*

/*
 * @author Sarthak Patel
 * @description Book Appointment fragment to submit the form to for appointment
 */

/* Reference taken from
 * https://www.digitalocean.com/community/tutorials/android-spinner-using-kotlin
 * https://learntodroid.com/how-to-move-between-fragments-using-the-navigation-component/
 * https://www.tutorialspoint.com/how-to-use-date-time-picker-dialog-in-kotlin-android
 * https://firebase.google.com/docs/database/android/read-and-write#kotlin+ktx_5
 */
class BookAppointment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_book_appointment, container, false)
        val sp : Spinner = view.findViewById(R.id.subjectDropDown)
        var subjects = arrayOf("Science","Maths","Physics","Computer Science")


        //configuring the subject list dropdown
        val spinnerAdapter : ArrayAdapter<String> = ArrayAdapter(requireActivity(), androidx.transition.R.layout.support_simple_spinner_dropdown_item, subjects)
        sp.adapter = spinnerAdapter

        var selectedItem = ""
        sp.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ){
                selectedItem = subjects[position]
                if (position != 0) {
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        val appointmentDate = view.findViewById<EditText>(R.id.appointmentDate)
        appointmentDate.showSoftInputOnFocus = false;
        var date = ""

        //setting up onclick listener for date picker while appointment booking
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

        // setting up the onclick listener on submit button
        val bookAppointmentBtn1 = view.findViewById<Button>(R.id.bookAppointmentBtn1)
        bookAppointmentBtn1.setOnClickListener{
            val appointmentDescription = view.findViewById<EditText>(R.id.appointmentDescription).text.toString()

            if(appointmentDate.text.isNotEmpty() && appointmentDate.text.toString().isNotEmpty() && selectedItem.isNotEmpty()){

                // creating the data for storing in the firebase
                var bookingMap = hashMapOf(
                    "bookingDesc" to appointmentDescription,
                    "tutorName" to arguments?.get("teacherName").toString(),
                    "userEmail" to Companion.email,
                    "teacherEmail" to arguments?.get("teacherEmail").toString(),
                    "bookingDate" to Timestamp(Date(appointmentDate.text.toString())),
                    "bookingSubject" to selectedItem
                )

                val booking = DatabaseSingleton.getBookingsReference()

                //setting up the data in the firebase document
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
            }else{
                Toast.makeText(context, "Enter the details", Toast.LENGTH_LONG).show()
            }
        }

        return view
    }
}