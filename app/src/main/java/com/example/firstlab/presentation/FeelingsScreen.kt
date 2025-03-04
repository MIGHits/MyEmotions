package com.example.firstlab.presentation

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.firstlab.R
import com.example.firstlab.adapter.FeelingsRecyclerAdapter
import com.example.firstlab.databinding.FeelingsScreenBinding
import com.example.firstlab.models.FeelingItem

class FeelingsScreen : Fragment(R.layout.feelings_screen) {

    private var binding: FeelingsScreenBinding? = null
    private lateinit var adapter: FeelingsRecyclerAdapter
    private var feelingList: List<FeelingItem>? = null
    private var postsAmount: Int? = null

    companion object {
        const val ARG_STAT_DATA = "ARG_FEELINGS_LiST"
        const val ARG_POST_AMOUNT = "ARG_POSTS"

        fun setData(data: List<FeelingItem>, posts: Int): StatisticMainFragment {
            return StatisticMainFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_POST_AMOUNT, posts)
                    putParcelableArrayList(ARG_STAT_DATA, ArrayList(data))
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        feelingList = arguments?.getParcelableArrayList(ARG_STAT_DATA)
        postsAmount = arguments?.getInt(ARG_POST_AMOUNT)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = FeelingsScreenBinding.bind(view)

        ViewCompat.setOnApplyWindowInsetsListener(view) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0)
            insets
        }

        binding?.addButton?.setOnClickListener {
            view.findNavController().navigate(R.id.feelingsScreenToNoteNavigation)
        }

        val layoutManager = LinearLayoutManager(requireContext())
        adapter =
            FeelingsRecyclerAdapter { findNavController().navigate(R.id.action_feelingsScreen3_to_addNoteScreen2) }

        feelingList?.let {
            adapter.feelingsList = it
        }

        binding?.notesCount?.text =
            postsAmount?.let { resources.getQuantityString(R.plurals.posts_count, it, postsAmount) }
        binding?.notesStreak?.text =
            postsAmount?.let { resources.getQuantityString(R.plurals.posts_count, it, postsAmount) }
        binding?.notesGoal?.text =
            postsAmount?.let { resources.getQuantityString(R.plurals.posts_count, it, postsAmount) }


        binding?.recyclerView?.layoutManager = layoutManager
        binding?.recyclerView?.adapter = adapter
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}