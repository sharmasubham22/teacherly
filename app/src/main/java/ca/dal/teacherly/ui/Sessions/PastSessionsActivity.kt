package ca.dal.teacherly.ui.Sessions

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import ca.dal.teacherly.R
import ca.dal.teacherly.adapters.SessionsAdapter
import ca.dal.teacherly.data.InitialSessions
import kotlinx.android.synthetic.main.activity_past_sessions.*

class PastSessionsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_past_sessions)



        PastSessionsRV.adapter = SessionsAdapter(InitialSessions.getAll())
        PastSessionsRV.layoutManager = LinearLayoutManager(this)
    }
}