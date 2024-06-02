package com.example.carvia.adapter

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.carvia.R
import com.example.carvia.insurance.db.Osago
import java.time.LocalDate


class OsagoAdapter(private val osagoList: ArrayList<Osago>): RecyclerView.Adapter<OsagoAdapter.myViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.osago_item, parent, false)
        return myViewHolder(itemView)

    }

    override fun getItemCount(): Int {
        return osagoList.size
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: myViewHolder, position: Int) {
        val currentItem = osagoList[position]
        holder.name.text = currentItem.name
        holder.type.text = currentItem.type_auto

        holder.foreign.text = currentItem.foreign_auto
        holder.diagnostic.text = currentItem.diagnostic_card
        holder.exp.text = currentItem.experience
        holder.date.text = currentItem.date_order
        holder.price.text = currentItem.price.toString()+" сом"
        holder.date_start.text = currentItem.date_order
        holder.period.text = currentItem.period
        holder.date_end.text = currentItem.date_order_end

    }
    class myViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
        val name:TextView = itemView.findViewById(R.id.insurance_name_osago)
        val type:TextView = itemView.findViewById(R.id.insurance_type_osago)
        val foreign:TextView = itemView.findViewById(R.id.insurance_foreign_osago)
        val diagnostic:TextView = itemView.findViewById(R.id.insurance_diagnostic_osago)
        val exp:TextView = itemView.findViewById(R.id.insurance_exp_osago)
        val date:TextView = itemView.findViewById(R.id.insurance_date_osago)
        val price:TextView = itemView.findViewById(R.id.insurance_price_osago)
        val date_start:TextView = itemView.findViewById(R.id.insurance_date_start_osago)
        val date_end:TextView = itemView.findViewById(R.id.insurance_date_end_osago)
        val period:TextView = itemView.findViewById(R.id.insurance_period_osago)
    }
}
