package com.example.reminderapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class myAdapter(private val dataholder: ArrayList<Model>) : RecyclerView.Adapter<myAdapter.MyViewholder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewholder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.single_reminder_file,
            parent,
            false
        ) // inflates the xml file in recyclerview
        return MyViewholder(view)
    }

    override fun onBindViewHolder(holder: MyViewholder, position: Int) {
        holder.mTitle.text = dataholder[position].title // Binds the single reminder objects to recycler view
        holder.mDate.text = dataholder[position].date
        holder.mTime.text = dataholder[position].time
    }

    override fun getItemCount(): Int {
        return dataholder.size
    }

    inner class MyViewholder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var mTitle: TextView = itemView.findViewById(R.id.txtTitle)
        var mDate: TextView = itemView.findViewById(R.id.txtDate)
        var mTime: TextView = itemView.findViewById(R.id.txtTime)
    }
}
