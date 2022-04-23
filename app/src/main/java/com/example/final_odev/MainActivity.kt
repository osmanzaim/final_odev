package com.example.final_odev

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.final_odev.databinding.ActivityMainBinding
import com.example.final_odev.databinding.TabLayoutBinding
import com.example.final_odev.fragments.GezdiklerimFragment
import com.example.final_odev.fragments.GezileceklerFragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {
    lateinit var binding:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        viewPagerOlustur()

        TabLayoutMediator(binding.tabLayout, binding.viewPager){
                tab,position ->
        }.attach()

        tabOlustur()

    }


    private fun tabOlustur() {

        var tab = TabLayoutBinding.inflate(layoutInflater)
        tab.tvBaslik.text = "Gezilecekler"
        tab.imageView.setImageResource(R.drawable.home)
        binding.tabLayout.getTabAt(0)!!.setCustomView(tab.root)

        tab = TabLayoutBinding.inflate(layoutInflater)
        tab.tvBaslik.text = "Gezdiklerim"
        tab.imageView.setImageResource(R.drawable.person)
        binding.tabLayout.getTabAt(1)!!.setCustomView(tab.root)


    }

    private fun viewPagerOlustur() {
        val adapter = ViewPagerAdapter(this)
        adapter.fragmentEkle(GezdiklerimFragment())
        adapter.fragmentEkle(GezileceklerFragment())

        binding.viewPager.adapter = adapter
    }


    internal class ViewPagerAdapter(activity: FragmentActivity): FragmentStateAdapter(activity){
        private val fragmentList = ArrayList<Fragment>()

        override fun getItemCount(): Int {
            return fragmentList.size
        }

        override fun createFragment(position: Int): Fragment {
            return fragmentList.get(position)
        }

        fun fragmentEkle(fragment:Fragment){
            fragmentList.add(fragment)
        }

    }

}







