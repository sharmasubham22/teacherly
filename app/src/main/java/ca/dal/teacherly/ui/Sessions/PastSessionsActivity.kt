package ca.dal.teacherly.ui.Sessions

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ca.dal.teacherly.R
import ca.dal.teacherly.controllers.SessionController
import kotlinx.android.synthetic.main.activity_past_sessions.*

/*
 * @author Bharatwaaj Shankaranarayanan
 * @description Past Sessions Activity to display a list of past sessions of the current user from database
 */
class PastSessionsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_past_sessions)

        // Get Shared Preferences to fetch current logged in user's email
        val sharedPref = this?.getPreferences(Context.MODE_PRIVATE) ?: return;

        var userEmail: String = sharedPref.getString("Email", "dhairyashah051@gmail.com").toString()

        // Call the session controller to initialize the sessions data for the respectivce user
        SessionController.initializeSessions(this, PastSessionsRV, userEmail)
    }
}