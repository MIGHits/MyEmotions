package com.example.firstlab.presentation.adapter

import android.annotation.SuppressLint
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.firstlab.R
import com.example.firstlab.databinding.WeeklyEmotionsItemBinding
import com.example.firstlab.presentation.mapper.dpToPx
import com.example.firstlab.presentation.models.WeeklyEmoteItem

class WeeklyEmotesAdapter : RecyclerView.Adapter<WeeklyEmotesAdapter.EmotesViewHolder>() {
    inner class EmotesViewHolder(val binding: WeeklyEmotionsItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    var emotesList: List<WeeklyEmoteItem> = emptyList()
        @SuppressLint("NotifyDataSetChanged")
        set(newValue) {
            field = newValue
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmotesViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = WeeklyEmotionsItemBinding.inflate(inflater, parent, false)

        return EmotesViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return emotesList.size
    }

    override fun onBindViewHolder(holder: EmotesViewHolder, position: Int) {
        val item = emotesList[position]
        val context = holder.itemView.context

        holder.binding.apply {
            weekDay.text = item.weekDay
            date.text = item.date
            if (item.emotes.isNotEmpty()) {
                item.emotes.forEach { emote ->
                    val iconImage = ImageView(context).apply {
                        setImageResource(emote.icon)
                        setPaddingRelative(4, 4, 4, 4)
                        layoutParams =
                            ViewGroup.LayoutParams(40.dpToPx().toInt(), 40.dpToPx().toInt())
                    }

                    val textEmote = TextView(context)
                    textEmote.text = emote.name
                    textEmote.setTextAppearance(R.style.labelThin)
                    textEmote.setTextColor(ContextCompat.getColor(context, R.color.weeklyEmotion))
                    textEmote.maxLines = 1
                    textEmote.ellipsize = TextUtils.TruncateAt.END;

                    emotesIcons.addView(iconImage)
                    weekEmotes.addView(textEmote)
                }
            } else {
                emptyEmotion.visibility = View.VISIBLE
            }
        }
    }
}