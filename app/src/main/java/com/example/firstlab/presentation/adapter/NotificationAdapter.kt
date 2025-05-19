package com.example.firstlab.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.firstlab.databinding.NotificationRecyclerItemBinding

class NotificationAdapter : RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder>() {

    inner class NotificationViewHolder(val binding: NotificationRecyclerItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    var notificationList: MutableList<String> = mutableListOf()
        @SuppressLint("NotifyDataSetChanged")
        set(newValue) {
            field = newValue
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = NotificationRecyclerItemBinding.inflate(inflater, parent, false)
        return NotificationViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return notificationList.size
    }

    override fun onBindViewHolder(holder: NotificationViewHolder, position: Int) {
        val currentNotification = notificationList[position]
        holder.binding.apply {
            chosenTime.text = currentNotification
            deleteButton.setOnClickListener {
                notificationList.removeAt(position)
                notifyItemRemoved(position)
                notifyItemRangeChanged(position, notificationList.size)
            }
        }
    }
}