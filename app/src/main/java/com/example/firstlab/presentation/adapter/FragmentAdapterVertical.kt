package com.example.firstlab.presentation.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class FragmentAdapterVertical(
    fragment: Fragment,
    var fragments: List<Fragment>,
) :
    FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
        return fragments.size
    }

    override fun getItemId(position: Int): Long {
        return fragments[position].hashCode().toLong()
    }

    override fun containsItem(itemId: Long): Boolean {
        return fragments.any { it.hashCode().toLong() == itemId }
    }

    fun updateFragments(newFragments: List<Fragment>) {
        fragments = newFragments
        notifyDataSetChanged()
    }

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }
}