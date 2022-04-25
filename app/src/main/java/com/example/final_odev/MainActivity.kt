package com.example.final_odev

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.final_odev.View.GezilecekYer
import com.example.final_odev.View.OncelikDurumu
import com.example.final_odev.View.gezilecekYerList
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

        veriDoldur()
        viewPagerOlustur()
        TabLayoutMediator(binding.tabLayout, binding.viewPager){
                tab,position ->
        }.attach()
        tabOlustur()


    }

    fun veriDoldur() { //şimdilik veritabanı olmadan recyclerview çalışıyor mu gormek için hazırlyıorum.

        val gezilecekYer = GezilecekYer("Karagöl","Borçka Karagöl","Tabiat ve doğayla iç içe bir yer.",null,R.drawable.karagol,OncelikDurumu.YUKSEK)
        gezilecekYerList.add(gezilecekYer)
        val gezilecekYer2 = GezilecekYer("Mençuna Şelalesi","Büyük bir şelale","Doğa içerisinde büyük bir şelale.",null,R.drawable.mencuna_selalesi,OncelikDurumu.ORTA)
        gezilecekYerList.add(gezilecekYer2)
        val gezilecekYer3 = GezilecekYer("Sümela Manastırı","Manastır","Trabzon'da bir manastır.",null,R.drawable.sumela_manastiri,OncelikDurumu.DUSUK)
        gezilecekYerList.add(gezilecekYer3)

        val gezilecekYer4 = GezilecekYer("Sümela Manastırı","Manastır","Trabzon'da bir manastır.",null,R.drawable.sumela_manastiri,OncelikDurumu.DUSUK)
        gezilecekYerList.add(gezilecekYer4)

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
        adapter.fragmentEkle(GezileceklerFragment())
        adapter.fragmentEkle(GezdiklerimFragment())

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







