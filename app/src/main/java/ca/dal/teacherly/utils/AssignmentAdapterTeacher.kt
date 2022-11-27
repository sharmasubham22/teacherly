package ca.dal.teacherly.utils

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ca.dal.teacherly.R

//adapter class to show list of assignments in recyclerview
/* reference: [1] https://www.youtube.com/watch?v=EoJX7h7lGxM&t=465s
[2] https://www.youtube.com/watch?v=Ly0xwWlUpVM&t=555s*/
class AssignmentAdapterTeacher(private val assignList:ArrayList<Assignments>) : RecyclerView.Adapter<AssignmentAdapterTeacher.MyViewHolder>(){
    private lateinit var mListener: onItemClickListener
    interface onItemClickListener{
        fun onItemClick(position: Int)
    }
    fun setOnItemCickListener(listener: onItemClickListener){
        mListener=listener
    }

    override fun onBindViewHolder(holder: AssignmentAdapterTeacher.MyViewHolder, position: Int) {
        val assignment:Assignments=assignList[position]
        holder.atitle.text=assignment.Title
        holder.dues.text=assignment.DueDate
        holder.comm.text=assignment.submissionComments
    }
    override fun getItemCount(): Int {
        return assignList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AssignmentAdapterTeacher.MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.assignments_teacher,parent,false)
        return MyViewHolder(itemView, mListener)
    }

    public class MyViewHolder(itemView: View, listener: onItemClickListener):RecyclerView.ViewHolder(itemView){
        val atitle : TextView=itemView.findViewById(R.id.textViewName)
        val dues : TextView=itemView.findViewById(R.id.textViewDate)
        val comm : TextView=itemView.findViewById(R.id.textViewInst)

        init {
            itemView.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }
    }
}