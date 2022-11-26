package ca.dal.teacherly.adapters

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import ca.dal.teacherly.R
import ca.dal.teacherly.models.Tutor
import ca.dal.teacherly.utils.Constants
import com.squareup.picasso.Picasso
import ca.dal.teacherly.ui.Home.HomeFragmentDirections

/*
 * @author Bharatwaaj Shankaranarayanan
 * @description Tutors Recycler View Adapter Class
 */
class TutorsAdapter(private val tutors: List<Tutor>) :

    RecyclerView.Adapter<TutorsAdapter.ViewHolder>() {

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tutorTitleTv: TextView
        val tutorCostTv: TextView
        val tutorImage: ImageView

        init {
            tutorTitleTv = view.findViewById<TextView>(R.id.tutorTitle)
            tutorCostTv = view.findViewById<TextView>(R.id.tutorCost)
            tutorImage = view.findViewById<ImageView>(R.id.tutorImage)
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.tutor_card_layout, viewGroup, false)

        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        val (tutorName, createdAt, updatedAt, phone, email, costPerHour, tutorImageURL) = tutors[position]
        viewHolder.tutorTitleTv.text = tutorName
        viewHolder.tutorCostTv.text = "$costPerHour/hr"

        val btnSearchTutors = viewHolder.itemView.findViewById<Button>(R.id.bookTeacherBtn)

        btnSearchTutors.setOnClickListener{
            val navController = Navigation.findNavController(viewHolder.itemView)
            var bundle = Bundle()
            bundle.putString("teacherEmail",email);
            bundle.putString("teacherCost",costPerHour);
            bundle.putString("teacherImageURL",tutorImageURL);
            bundle.putString("teacherName",tutorName);
            bundle.putString("teacherPhone",phone);
            var action = HomeFragmentDirections.actionNavigationHomeFragmentToTeacherDetails()

            navController.navigate(R.id.action_navigationHomeFragment_to_teacherDetails,bundle)
        }

        if(tutorImageURL != "" || tutorImageURL != null){
            Picasso.get()
                .load(tutorImageURL)
                .resize(64,64)
                .into(viewHolder.tutorImage);
        } else {
            Picasso.get()
                .load(Constants.DEFAULT_IMAGE_URL)
                .resize(64,64)
                .into(viewHolder.tutorImage)
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = tutors.size

}
