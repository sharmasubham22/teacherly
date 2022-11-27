package ca.dal.teacherly.utils


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewParent
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ca.dal.teacherly.R

class AssignmentAdapter(private val assignList:ArrayList<Assignments>) : RecyclerView.Adapter<AssignmentAdapter.MyViewHolder>(){
private lateinit var mListener: onItemClickListener
    interface onItemClickListener{
        fun onItemClick(position: Int)
    }
    fun setOnItemCickListener(listener: onItemClickListener){
        mListener=listener
    }

    override fun onBindViewHolder(holder: AssignmentAdapter.MyViewHolder, position: Int) {
        val assignment:Assignments=assignList[position]
        holder.atitle.text=assignment.Title
        holder.dues.text=assignment.DueDate
        holder.inst.text=assignment.Instructions
        holder.grd.text=assignment.Grade
    }
    override fun getItemCount(): Int {
        return assignList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AssignmentAdapter.MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.assignments,parent,false)

        return MyViewHolder(itemView, mListener)
    }

    public class MyViewHolder(itemView: View, listener: onItemClickListener):RecyclerView.ViewHolder(itemView){
        val atitle : TextView=itemView.findViewById(R.id.textViewName)
        val dues : TextView=itemView.findViewById(R.id.textViewDate)
        val inst : TextView=itemView.findViewById(R.id.textViewInst)
        val grd : TextView=itemView.findViewById(R.id.textViewGrade)


        init {
            itemView.setOnClickListener {
                listener.onItemClick(adapterPosition)
}
        }
    }
}