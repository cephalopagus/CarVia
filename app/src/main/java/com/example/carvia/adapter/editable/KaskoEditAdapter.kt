package com.example.carvia.adapter.editable

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.carvia.R
import com.example.carvia.insurance.db.Kasko
import com.example.carvia.insurance.db.Osago

class KaskoEditAdapter(private var kaskoListEdit:ArrayList<Kasko>, clickListener: KaskoEditAdapter.ClickListener):
    RecyclerView.Adapter<KaskoEditAdapter.myViewHolder>()  {

    private var clickListener: ClickListener = clickListener
    interface ClickListener{
        fun clickedDocs(kasko: Kasko){

        }
    }
    class myViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.insurance_name_kasko)
        val model: TextView = itemView.findViewById(R.id.insurance_model_kasko)
        val year: TextView = itemView.findViewById(R.id.insurance_year_kasko)
        val price_car: TextView = itemView.findViewById(R.id.insurance_price_car_kasko)
        val procent: TextView = itemView.findViewById(R.id.insurance_proc_kasko)
        val date: TextView = itemView.findViewById(R.id.insurance_date_kasko)
        val price: TextView = itemView.findViewById(R.id.insurance_price_kasko)
        val date_start: TextView = itemView.findViewById(R.id.insurance_date_start_kasko)
        val date_end: TextView = itemView.findViewById(R.id.insurance_date_end_kasko)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.kasko_item, parent, false)
        return myViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return kaskoListEdit.size
    }


    override fun onBindViewHolder(holder: myViewHolder, position: Int) {
        val currentItem = kaskoListEdit[position]
        holder.name.text = currentItem.name
        holder.model.text = currentItem.model_auto
        holder.year.text = currentItem.year_auto.toString()
        holder.price_car.text = currentItem.price_auto.toString()+" сом"
        holder.procent.text = currentItem.procent.toString() + "%"
        holder.date.text = currentItem.date_order
        holder.date_start.text = currentItem.date_order
        holder.date_end.text = currentItem.date_order_end
        holder.price.text = currentItem.price.toString()+" сом"
        holder.itemView.setOnClickListener {
            clickListener.clickedDocs(currentItem)
        }

    }

}
