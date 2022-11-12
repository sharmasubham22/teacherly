package ca.dal.teacherly.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ca.dal.teacherly.R
import ca.dal.teacherly.models.Subject

class SubjectsAdapter(private val subjects: List<Subject>) :

    RecyclerView.Adapter<SubjectsAdapter.ViewHolder>() {

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val subjectTitleTv: TextView
        val subjectImage: ImageView

        init {
            subjectTitleTv = view.findViewById<TextView>(R.id.subjectTitle)
            subjectImage = view.findViewById<ImageView>(R.id.subjectImage)
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.subjects_card_layout, viewGroup, false)

        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        val (subjectName, createdAt, updatedAt, subjectImageURL) = subjects[position]
        viewHolder.subjectTitleTv.text = subjectName

    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = subjects.size
}