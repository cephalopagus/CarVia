package com.example.carvia.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.carvia.AdminPanel
import com.example.carvia.MainActivity
import com.example.carvia.R
import com.example.carvia.WelcomeScreen
import com.example.carvia.auth.Users
import com.google.android.material.button.MaterialButton

class AdminUserAdapter(private var adminUserList:ArrayList<Users>, clickListener: ClickListener):
    RecyclerView.Adapter<AdminUserAdapter.myViewHolder>()  {

    private var clickListener: ClickListener = clickListener
    interface ClickListener{
        fun clickedItem(users: Users){

        }
    }
    class myViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.admin_user_name_list)
        val phone: TextView = itemView.findViewById(R.id.admin_user_phone_list)
        val email: TextView = itemView.findViewById(R.id.admin_user_mail_list)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.user_item, parent, false)
        return myViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return adminUserList.size
    }


    override fun onBindViewHolder(holder: myViewHolder, position: Int) {
        val currentItem = adminUserList[position]
        holder.name.text = currentItem.name
        holder.phone.text = currentItem.phone
        holder.email.text = currentItem.email

        holder.itemView.setOnClickListener {
            clickListener.clickedItem(currentItem)
        }





    }




}