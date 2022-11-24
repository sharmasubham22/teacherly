package ca.dal.teacherly.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ca.dal.teacherly.R
import ca.dal.teacherly.models.Sessions
import ca.dal.teacherly.models.Tutor
import com.squareup.picasso.Picasso

class SessionsAdapter(private val sessions: List<Sessions>) :

    RecyclerView.Adapter<SessionsAdapter.ViewHolder>() {

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val bookingId: TextView
        val bookingDate: TextView
        val bookingSubject: TextView
        val tutorName: TextView
        val tutorNameCV: TextView

        init {
            bookingId = view.findViewById<TextView>(R.id.bookingId)
            bookingDate = view.findViewById<TextView>(R.id.bookingDate)
            bookingSubject = view.findViewById<TextView>(R.id.bookingSubject)
            tutorName = view.findViewById<TextView>(R.id.tutorName)
            tutorNameCV = view.findViewById<TextView>(R.id.tutorNameCV)
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.sessions_card_layout, viewGroup, false)

        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        val (bookingId, bookingDate, description, subject, tutorName) = sessions[position]
        viewHolder.bookingId.text = "ID #" + bookingId
        viewHolder.bookingDate.text = bookingDate
        viewHolder.bookingSubject.text = subject
        viewHolder.tutorName.text = tutorName
        viewHolder.tutorNameCV.text = tutorName[0].toString()
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = sessions.size

}