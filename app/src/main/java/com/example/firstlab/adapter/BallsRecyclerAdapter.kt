package com.example.firstlab.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.children
import androidx.recyclerview.widget.RecyclerView
import com.example.firstlab.databinding.EmotionsRecyclerItemBinding
import com.example.firstlab.models.BallsItem

class BallsRecyclerAdapter(
    private val showEmotion: (Int, String) -> Unit,
    private val hideEmotion: () -> Unit
) :
    RecyclerView.Adapter<BallsRecyclerAdapter.BallsViewHolder>() {

    private var previousSelectedIndex = -1

    class BallsViewHolder(val binding: EmotionsRecyclerItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    var ballsList: List<BallsItem> = emptyList()
        @SuppressLint("NotifyDataSetChanged")
        set(newValue) {
            field = newValue
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BallsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = EmotionsRecyclerItemBinding.inflate(inflater, parent, false)
        return BallsViewHolder(binding)
    }

    override fun getItemCount(): Int = ballsList.size

    override fun getItemViewType(position: Int): Int = position

    fun getItem(position: Int): BallsItem = ballsList[position]

    override fun onBindViewHolder(holder: BallsViewHolder, position: Int) {
        val ball = ballsList[position]

        with(holder.binding) {
            itemContainer.setCardBackgroundColor(ball.color)
            emotionBall.text = ball.name

            emotionBall.setOnClickListener {
                handleItemClick(holder, ball, position)
            }
        }
    }

    private fun handleItemClick(holder: BallsViewHolder, ball: BallsItem, position: Int) {
        val parent = holder.itemView.parent as RecyclerView

        if (ball.isSelected) {
            resetSelectedState(parent, ball)
        } else {
            if (previousSelectedIndex != -1) {
                resetSelectedState(parent, getItem(previousSelectedIndex))
            }
            animateSelectedItem(holder, parent, position)
        }
    }

    private fun resetSelectedState(parent: RecyclerView, ball: BallsItem) {
        val previousHolder = parent.findViewHolderForAdapterPosition(previousSelectedIndex)
        previousHolder?.itemView?.animate()
            ?.scaleX(1f)
            ?.scaleY(1f)
            ?.setDuration(200)
            ?.start()

        parent.children.forEach { view ->
            view.animate().translationX(0f).translationY(0f).setDuration(200).start()
        }
        ball.isSelected = false
        hideEmotion()
    }

    private fun animateSelectedItem(holder: BallsViewHolder, parent: RecyclerView, position: Int) {
        val currentView = holder.itemView
        val currentX = currentView.left
        val currentY = currentView.top

        showEmotion(ballsList[position].color, ballsList[position].name)

        holder.binding.itemContainer.animate()
            .scaleX(1.35f)
            .scaleY(1.35f)
            .setDuration(200)
            .start()

        parent.children.forEach { view ->
            val childX = view.left
            val childY = view.top

            when {
                childY == currentY && childX < currentX -> view.animate()
                    .translationX(-40f)
                    .setDuration(200)
                    .start()

                childY == currentY && childX > currentX -> view.animate()
                    .translationX(40f)
                    .setDuration(200)
                    .start()

                childX == currentX && childY < currentY -> view.animate()
                    .translationY(-40f)
                    .setDuration(200)
                    .start()

                childX == currentX && childY > currentY -> view.animate()
                    .translationY(40f)
                    .setDuration(200)
                    .start()
            }
        }

        previousSelectedIndex = position
        ballsList[position].isSelected = true
    }
}