package ca.dal.teacherly.utils


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewParent
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ca.dal.teacherly.R

class AssignmentAdapter(private val assignList:ArrayList<Assignments>) : RecyclerView.Adapter<AssignmentAdapter.MyViewHolder>(){
    

    override fun onBindViewHolder(holder: AssignmentAdapter.MyViewHolder, position: Int) {
        val assignment:Assignments=assignList[position]
        holder.atitle.text=assignment.Title
        holder.inst.text=assignment.Instructions
    }
    override fun getItemCount(): Int {
        return assignList.size
    }
    public class MyViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val atitle : TextView=itemView.findViewById(R.id.textViewName)
        val inst : TextView=itemView.findViewById(R.id.textViewInst)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AssignmentAdapter.MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.assignments,parent,false)

        return MyViewHolder(itemView)
    }
}