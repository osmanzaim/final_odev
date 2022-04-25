package com.example.final_odev.fragments

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.example.final_odev.MainActivity
import com.example.final_odev.R
import com.example.final_odev.databinding.ActivityDetayBinding
import com.example.final_odev.databinding.FragmentGezdiklerimBinding
import com.example.final_odev.databinding.TabLayoutBinding
import com.google.android.material.tabs.TabLayoutMediator

class DetayActivity : AppCompatActivity() {
    lateinit var binding: ActivityDetayBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetayBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewPagerOlustur()
        TabLayoutMediator(binding.tabLayout,binding.viewPager2){
                tab,position ->
        }.attach()
        tabOlustur()

        imgSliderOlustur()



    }


    fun imgSliderOlustur() {
        var imageList = ArrayList<SlideModel>()

        imageList.add(SlideModel(R.drawable.karagol,""))
        imageList.add(SlideModel(R.drawable.mencuna_selalesi,""))
        imageList.add(SlideModel(R.drawable.sumela_manastiri,""))

        binding.imgSlider.setImageList(imageList,ScaleTypes.FIT)

    }
    private fun viewPagerOlustur() {
        val adapter = MainActivity.ViewPagerAdapter(this)
        adapter.fragmentEkle(GezileceklerFragment())
        adapter.fragmentEkle(GezdiklerimFragment())

        binding.viewPager2.adapter = adapter
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
}