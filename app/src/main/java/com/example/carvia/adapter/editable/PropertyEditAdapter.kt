package com.example.carvia.adapter.editable

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.carvia.R
import com.example.carvia.insurance.db.Kasko
import com.example.carvia.insurance.db.Property

class PropertyEditAdapter (private var propertyListEdit:ArrayList<Property>, clickListener: PropertyEditAdapter.ClickListener):
    RecyclerView.Adapter<PropertyEditAdapter.myViewHolder>()  {

    private var clickListener: ClickListener = clickListener
    interface ClickListener{
        fun clickedDocs(property: Property){

        }
    }
    class myViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.insurance_name_property)
        val birth: TextView = itemView.findViewById(R.id.insurance_birth_property)
        val pasport: TextView = itemView.findViewById(R.id.insurance_pasport_property)
        val pasport_date: TextView = itemView.findViewById(R.id.insurance_pasport_date_property)
        val pasport_house: TextView = itemView.findViewById(R.id.insurance_pasport_house_property)
        val pasport_id: TextView = itemView.findViewById(R.id.insurance_pasport_id_property)
        val address: TextView = itemView.findViewById(R.id.insurance_address_property)

        val finishing: TextView = itemView.findViewById(R.id.insurance_finishing_property)
        val decoration: TextView = itemView.findViewById(R.id.insurance_decoration_property)
        val responsibility: TextView = itemView.findViewById(R.id.insurance_responsibility_property)


        val date: TextView = itemView.findViewById(R.id.insurance_date_property)
        val price: TextView = itemView.findViewById(R.id.insurance_price_property)
        val date_start: TextView = itemView.findViewById(R.id.insurance_date_start_property)
        val date_end: TextView = itemView.findViewById(R.id.insurance_date_end_property)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.property_item, parent, false)
        return myViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return propertyListEdit.size
    }


    override fun onBindViewHolder(holder: myViewHolder, position: Int) {
        val currentItem = propertyListEdit[position]
        holder.name.text = currentItem.name
        holder.birth.text = currentItem.birth
        holder.pasport.text = currentItem.pasport
        holder.pasport_date.text = currentItem.pasport_date
        holder.pasport_house.text = currentItem.pasport_house
        holder.pasport_id.text = currentItem.pasport_id
        holder.address.text = currentItem.address


        holder.finishing.text = currentItem.finishing+" сом"
        holder.decoration.text = currentItem.decoration+" сом"
        holder.responsibility.text = currentItem.responsibility+" сом"


        holder.date_start.text = currentItem.date_order
        holder.date.text = currentItem.date_order
        holder.date_end.text = currentItem.date_order_end
        holder.price.text = currentItem.price.toString()+" сом"
        holder.itemView.setOnClickListener {
            clickListener.clickedDocs(currentItem)
        }

    }

}
