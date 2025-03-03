package com.example.firstlab.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.firstlab.databinding.FeelingsRecyclerItemBinding
import com.example.firstlab.models.FeelingItem

class FeelingsRecyclerAdapter:RecyclerView.Adapter<FeelingsRecyclerAdapter.FeelingsViewHolder>(){
    class FeelingsViewHolder(val binding:FeelingsRecyclerItemBinding):RecyclerView.ViewHolder(binding.root)
    var feelingsList:List<FeelingItem> = emptyList()
        @SuppressLint("NotifyDataSetChanged")
        set(newValue){
            field = newValue
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeelingsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = FeelingsRecyclerItemBinding.inflate(inflater,parent,false)

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
            emotion.text = feelingItem.name
            feelingIcon.setImageResource(feelingItem.icon)
            emotion.setTextColor(feelingItem.color)
        }
    }
}