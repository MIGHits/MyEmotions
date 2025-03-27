package com.example.firstlab.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.firstlab.R
import com.example.firstlab.customView.EmotesFrequencyStripeView
import com.example.firstlab.databinding.MostFrequentItemBinding
import com.example.firstlab.models.Emotion
import com.example.firstlab.models.EmotionType

class EmotesFrequencyAdapter :
    RecyclerView.Adapter<EmotesFrequencyAdapter.EmotesFrequentViewHolder>() {
    var emotesList = emptyList<Emotion>()

        @SuppressLint("NotifyDataSetChanged")
        set(newValue) {
            field = newValue.sortedByDescending { it.frequent }
            notifyDataSetChanged()
        }

    inner class EmotesFrequentViewHolder(val binding: MostFrequentItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmotesFrequentViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = MostFrequentItemBinding.inflate(inflater, parent, false)

        return EmotesFrequentViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return emotesList.size
    }

    override fun onBindViewHolder(holder: EmotesFrequentViewHolder, position: Int) {
        val currentEmote = emotesList[position]
        val context = holder.itemView.context
        holder.binding.apply {
            emoteIcon.setImageResource(currentEmote.icon)
            emoteName.text = currentEmote.name
            currentEmote.type?.let { setStripeGradient(emoteStripe, it, context) }
            setEmoteFrequency(emoteStripe, currentEmote.frequent)
            emoteStripe.setPercent((currentEmote.frequent.toFloat() / emotesList[0].frequent))
        }
    }

    private fun setStripeGradient(
        stripe: EmotesFrequencyStripeView,
        type: EmotionType,
        context: Context
    ) {
        when (type) {
            EmotionType.BLUE -> stripe.setGradientColors(
                intArrayOf(
                    ContextCompat.getColor(context, R.color.blueEmoteSecond),
                    ContextCompat.getColor(context, R.color.blueEmoteFirst)
                )
            )

            EmotionType.GREEN -> stripe.setGradientColors(
                intArrayOf(
                    ContextCompat.getColor(context, R.color.greenEmoteSecond),
                    ContextCompat.getColor(context, R.color.greenEmoteFirst)
                )
            )

            EmotionType.RED -> stripe.setGradientColors(
                intArrayOf(
                    ContextCompat.getColor(context, R.color.redEmoteSecond),
                    ContextCompat.getColor(context, R.color.redEmoteFirst)
                )
            )

            EmotionType.YELLOW -> stripe.setGradientColors(
                intArrayOf(
                    ContextCompat.getColor(context, R.color.yellowEmoteSecond),
                    ContextCompat.getColor(context, R.color.yellowEmoteFirst)
                )
            )
        }

    }

    private fun setEmoteFrequency(stripe: EmotesFrequencyStripeView, frequency: Int) {
        stripe.setCardText(frequency.toString())
    }
}