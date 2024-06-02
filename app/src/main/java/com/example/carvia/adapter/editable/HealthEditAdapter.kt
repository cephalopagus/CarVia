package com.example.carvia.adapter.editable

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.carvia.R
import com.example.carvia.insurance.db.Health
import com.example.carvia.insurance.db.Kasko
import com.example.carvia.list_edit.SelectedDocKasko

class HealthEditAdapter(private var healthListEdit:ArrayList<Health>, clickListener: HealthEditAdapter.ClickListener):
    RecyclerView.Adapter<HealthEditAdapter.myViewHolder>()  {

    private var clickListener: ClickListener = clickListener
    interface ClickListener{
        fun clickedDocs(health: Health){

        }
    }
    class myViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.insurance_name_health)
        val birth: TextView = itemView.findViewById(R.id.insurance_birth_health)
        val pasport: TextView = itemView.findViewById(R.id.insurance_pasport_health)
        val pasport_date: TextView = itemView.findViewById(R.id.insurance_pasport_date_health)
        val pasport_house: TextView = itemView.findViewById(R.id.insurance_pasport_house_health)
        val pasport_id: TextView = itemView.findViewById(R.id.insurance_pasport_id_health)
        val address: TextView = itemView.findViewById(R.id.insurance_address_health)


        val date: TextView = itemView.findViewById(R.id.insurance_date_health)
        val price: TextView = itemView.findViewById(R.id.insurance_price_health)
        val date_start: TextView = itemView.findViewById(R.id.insurance_date_start_health)
        val date_end: TextView = itemView.findViewById(R.id.insurance_date_end_health)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.health_item, parent, false)
        return myViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return healthListEdit.size
    }


    override fun onBindViewHolder(holder: myViewHolder, position: Int) {
        val currentItem = healthListEdit[position]
        holder.name.text = currentItem.name
        holder.birth.text = currentItem.birth
        holder.pasport.text = currentItem.pasport
        holder.pasport_date.text = currentItem.pasport_date
        holder.pasport_house.text = currentItem.pasport_house
        holder.pasport_id.text = currentItem.pasport_id
        holder.address.text = currentItem.address
        holder.date_start.text = currentItem.date_order
        holder.date.text = currentItem.date_order
        holder.date_end.text = currentItem.date_order_end
        holder.price.text = currentItem.price.toString()+" сом"
        holder.itemView.setOnClickListener {
            clickListener.clickedDocs(currentItem)
        }

    }


}