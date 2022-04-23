package com.example.final_odev

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.final_odev.fragments.GezdiklerimFragment
import com.example.final_odev.fragments.GezileceklerFragment

class TabPageAdapter(activity: FragmentActivity, private val tabCount:Int): FragmentStateAdapter(activity) {
    override fun getItemCount(): Int {
        return tabCount
    }

    override fun createFragment(position: Int): Fragment {
        return when (position){
            0->GezileceklerFragment()
            1->GezdiklerimFragment()
            else -> GezileceklerFragment()
        }
    }
}