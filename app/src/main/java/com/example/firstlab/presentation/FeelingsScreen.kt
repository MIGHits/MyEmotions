package com.example.firstlab.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.firstlab.R
import com.example.firstlab.components.FeelingsRecyclerAdapter
import com.example.firstlab.databinding.FeelingsScreenBinding
import com.example.firstlab.models.FeelingItem

class FeelingsScreen : Fragment(R.layout.feelings_screen) {

    private var binding: FeelingsScreenBinding? = null
    private lateinit var adapter: FeelingsRecyclerAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = FeelingsScreenBinding.bind(view)

        ViewCompat.setOnApplyWindowInsetsListener(view) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0)
            insets
        }
        val layoutManager = LinearLayoutManager(requireContext())
        adapter = FeelingsRecyclerAdapter()
        adapter.feelingsList = listOf(
            FeelingItem(
                "вчера,23:40",
                "выгорание",
                R.drawable.sadness_icon,
                R.drawable.blue_type_gradient,
                ContextCompat.getColor(requireContext(), R.color.blueGradient)
            ),
            FeelingItem(
                "вчера,14:08",
                "спокойствие",
                R.drawable.mithosis_icon,
                R.drawable.green_type_gradient,
                ContextCompat.getColor(requireContext(), R.color.greenGradient)
            ),
            FeelingItem(
                "воскресенье,16:12",
                "продуктивность",
                R.drawable.lightning_icon,
                R.drawable.yellow_type_gradient,
                ContextCompat.getColor(requireContext(), R.color.yellowGradient)
            ),
            FeelingItem(
                "воскресенье,03:59",
                "беспокойство",
                R.drawable.soft_flower_icon,
                R.drawable.red_type_gradient,
                ContextCompat.getColor(requireContext(), R.color.redGradient)
            )
        )

        binding?.recyclerView?.layoutManager = layoutManager
        binding?.recyclerView?.adapter = adapter
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}