package com.example.firstlab.components

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class FragmentAdapterVertical(
    private val fragments: List<Fragment>,
    manager: FragmentManager,
    lifecycle: Lifecycle
) :
    FragmentStateAdapter(manager, lifecycle) {
    override fun getItemCount(): Int {
        return fragments.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }
}