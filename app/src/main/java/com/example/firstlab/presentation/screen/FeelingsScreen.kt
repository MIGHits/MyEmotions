package com.example.firstlab.presentation.screen

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.firstlab.R
import com.example.firstlab.common.Constant.ARG_EMOTION_ID
import com.example.firstlab.common.Constant.TOTAL_GOALS
import com.example.firstlab.presentation.adapter.FeelingsRecyclerAdapter
import com.example.firstlab.databinding.FeelingsScreenBinding
import com.example.firstlab.presentation.models.JournalItem
import com.example.firstlab.presentation.state.JournalState
import com.example.firstlab.presentation.viewModel.JournalViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.androidx.viewmodel.ext.android.viewModel

class FeelingsScreen : Fragment(R.layout.feelings_screen) {

    private var binding: FeelingsScreenBinding? = null
    private lateinit var adapter: FeelingsRecyclerAdapter
    private val viewModel by viewModel<JournalViewModel>()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = FeelingsScreenBinding.bind(view)

        ViewCompat.setOnApplyWindowInsetsListener(view) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0)
            insets
        }

        binding?.addButton?.setOnClickListener {
            findNavController().navigate(FeelingsScreenDirections.feelingsScreenToNoteNavigation())
        }

        val layoutManager = LinearLayoutManager(requireContext())
        adapter =
            FeelingsRecyclerAdapter { emotionId ->
                findNavController().navigate(
                    FeelingsScreenDirections.actionFeelingsScreen3ToAddNoteScreen2(emotionId)
                )
            }

        lifecycleScope.launch {
            viewModel.journalState.collect { journal ->
                when (journal) {
                    is JournalState.Loading -> {}
                    is JournalState.Content -> {
                        withContext(Dispatchers.Main) {
                            adapter.feelingsList = journal.content.emotions
                            binding?.apply {
                                notesCount.text = resources.getQuantityString(
                                    R.plurals.posts_count,
                                    journal.content.amountOFEmotions,
                                    journal.content.amountOFEmotions
                                )
                                notesStreak.text = resources.getQuantityString(
                                    R.plurals.days,
                                    journal.content.series,
                                    journal.content.series
                                )
                                customGoalCircleView.emotionsColors = journal.content.today
                            }
                        }
                    }

                    JournalState.Empty -> {
                        adapter.feelingsList = emptyList()
                    }
                }
            }
        }
        binding?.apply {
            notesGoal.text =
                resources.getQuantityString(
                    R.plurals.posts_count,
                    TOTAL_GOALS,
                    TOTAL_GOALS
                )

            recyclerView.layoutManager = layoutManager
            recyclerView.adapter = adapter
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    companion object {
        const val ARG_STAT_DATA = "ARG_FEELINGS_LiST"
        const val ARG_POST_AMOUNT = "ARG_POSTS"

        fun setData(data: List<JournalItem>, posts: Int): FeelingsScreen {
            return FeelingsScreen().apply {
                arguments = Bundle().apply {
                    putInt(ARG_POST_AMOUNT, posts)
                    putParcelableArrayList(ARG_STAT_DATA, ArrayList(data))
                }
            }
        }
    }
}