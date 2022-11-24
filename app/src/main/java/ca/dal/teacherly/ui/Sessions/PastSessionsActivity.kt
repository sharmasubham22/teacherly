package ca.dal.teacherly.ui.Sessions

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import ca.dal.teacherly.R
import ca.dal.teacherly.adapters.SessionsAdapter
import ca.dal.teacherly.data.InitialSessions
import ca.dal.teacherly.models.Sessions
import ca.dal.teacherly.utils.Constants
import ca.dal.teacherly.utils.Utils
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_past_sessions.*

class PastSessionsActivity : AppCompatActivity() {


    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_past_sessions)

        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        var ref = db.collection(Constants.FB_BOOKINGS_SCHEMA).get();

        InitialSessions.clearAll();

        ref.addOnSuccessListener {
            val size = it.documents.count() - 1
            for (idx in 0..size) {

                    var bookingId = it.documents.get(idx).id.toString()
                    var bookingDescription = it.documents.get(idx).get("bookingDesc")?.toString().toString()
                    var bookingDate:Timestamp = it.documents.get(idx).get("bookingDate") as Timestamp
                    var bookingSubject = it.documents.get(idx).get("bookingSubject")?.toString().toString()
                    var tutorName = it.documents.get(idx).get("tutorName")?.toString().toString()

                    InitialSessions.addSession(
                        Sessions(
                            bookingId,
                            Utils.getDateTime(bookingDate),
                            bookingDescription,
                            bookingSubject,
                            tutorName
                        )
                    );

            }

            PastSessionsRV.adapter = SessionsAdapter(InitialSessions.getAll())
            PastSessionsRV.layoutManager = LinearLayoutManager(this)
        }
    }
}