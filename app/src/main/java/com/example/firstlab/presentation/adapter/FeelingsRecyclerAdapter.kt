package com.example.firstlab.presentation.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.firstlab.databinding.FeelingsRecyclerItemBinding
import com.example.firstlab.presentation.models.JournalItem

class FeelingsRecyclerAdapter(val navigate: (Int) -> Unit) :
    RecyclerView.Adapter<FeelingsRecyclerAdapter.FeelingsViewHolder>() {
    class FeelingsViewHolder(val binding: FeelingsRecyclerItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    var feelingsList: List<JournalItem> = emptyList()
        @SuppressLint("NotifyDataSetChanged")
        set(newValue) {
            field = newValue
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeelingsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = FeelingsRecyclerItemBinding.inflate(inflater, parent, false)

        return FeelingsViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return feelingsList.size
    }

    override fun onBindViewHolder(holder: FeelingsViewHolder, position: Int) {
        val feelingItem = feelingsList[position]
        val context = holder.itemView.context

        holder.binding.apply {
            time.text = feelingItem.time
            feelingCard.setBackgroundResource(feelingItem.background)
            feelingCard.setOnClickListener { navigate(feelingItem.id) }
            emotion.text = feelingItem.name
            feelingIcon.setImageResource(feelingItem.icon)
            emotion.setTextColor(feelingItem.color)
        }
    }
}