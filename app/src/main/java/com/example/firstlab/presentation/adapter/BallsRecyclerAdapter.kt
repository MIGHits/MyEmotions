package com.example.firstlab.presentation.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.children
import androidx.recyclerview.widget.RecyclerView
import com.example.firstlab.common.Constant.BALLS_SCALE_DOWN
import com.example.firstlab.common.Constant.BALLS_SCALE_UP
import com.example.firstlab.common.Constant.BALLS_TRANSLATION
import com.example.firstlab.common.Constant.BASIC_ANIM_DURATION
import com.example.firstlab.common.Constant.MINIMUM_CONST
import com.example.firstlab.databinding.EmotionsRecyclerItemBinding
import com.example.firstlab.presentation.models.BallsItem

class BallsRecyclerAdapter(
    private val showEmotion: (Int, String) -> Unit,
    private val hideEmotion: () -> Unit,
) :
    RecyclerView.Adapter<BallsRecyclerAdapter.BallsViewHolder>() {

    private var previousSelectedIndex = MINIMUM_CONST

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
            ?.scaleX(BALLS_SCALE_DOWN)
            ?.scaleY(BALLS_SCALE_DOWN)
            ?.setDuration(BASIC_ANIM_DURATION)
            ?.start()

        parent.children.forEach { view ->
            view.animate().translationX(0f).translationY(0f).setDuration(BASIC_ANIM_DURATION)
                .start()
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
            .scaleX(BALLS_SCALE_UP)
            .scaleY(BALLS_SCALE_UP)
            .setDuration(BASIC_ANIM_DURATION)
            .start()

        parent.children.forEach { view ->
            val childX = view.left
            val childY = view.top

            when {
                childY == currentY && childX < currentX -> view.animate()
                    .translationX(-BALLS_TRANSLATION)
                    .setDuration(BASIC_ANIM_DURATION)
                    .start()

                childY == currentY && childX > currentX -> view.animate()
                    .translationX(BALLS_TRANSLATION)
                    .setDuration(BASIC_ANIM_DURATION)
                    .start()

                childX == currentX && childY < currentY -> view.animate()
                    .translationY(-BALLS_TRANSLATION)
                    .setDuration(BASIC_ANIM_DURATION)
                    .start()

                childX == currentX && childY > currentY -> view.animate()
                    .translationY(BALLS_TRANSLATION)
                    .setDuration(BASIC_ANIM_DURATION)
                    .start()
            }
        }

        previousSelectedIndex = position
        ballsList[position].isSelected = true
    }
}