package com.example.final_odev.View.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.final_odev.R
import com.example.final_odev.View.views.gezilecekYerList
import com.example.final_odev.databinding.ActivityMainBinding
import com.example.final_odev.databinding.TabLayoutBinding
import com.example.final_odev.fragments.GezdiklerimFragment
import com.example.final_odev.fragments.GezileceklerFragment
import com.example.final_odev.viewmodel.GezilecekYerLogic
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {
    lateinit var binding:ActivityMainBinding
    private lateinit var viewPagerAdapter2: ViewPagerAdapter2


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

        btnClickListener()

    }










    var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult(), ::duzenleResult )

    fun duzenleResult(result: ActivityResult) {
        if (result.resultCode == AppCompatActivity.RESULT_OK)
        {
            //val data = result.data

            //Toast.makeText(this,"sdfsdfdsf",Toast.LENGTH_LONG).show()

            gezilecekYerList = GezilecekYerLogic.tumGezilecekYerleriGetir(this)

            viewPagerAdapter2.refreshFragment(0,GezileceklerFragment())
            tabOlustur()


        }
    }

    private fun btnClickListener() {
        binding.floatingActionButton.setOnClickListener{
            var intent = Intent(this, ActivityYerEkle::class.java)
            resultLauncher.launch(intent)
        }
    }

    fun veriDoldur() {

        gezilecekYerList = GezilecekYerLogic.tumGezilecekYerleriGetir(this)


        /*
          val gezilecekYer = GezilecekYer("Karagöl","Borçka Karagöl","Tabiat ve doğayla iç içe bir yer.",null,R.drawable.karagol,OncelikDurumu.YUKSEK)
        gezilecekYerList.add(gezilecekYer)
        val gezilecekYer2 = GezilecekYer("Mençuna Şelalesi","Büyük bir şelale","Doğa içerisinde büyük bir şelale.",null,R.drawable.mencuna_selalesi,OncelikDurumu.ORTA)
        gezilecekYerList.add(gezilecekYer2)
        val gezilecekYer3 = GezilecekYer("Sümela Manastırı","Manastır","Trabzon'da bir manastır.",null,R.drawable.sumela_manastiri,OncelikDurumu.DUSUK)
        gezilecekYerList.add(gezilecekYer3)

        val gezilecekYer4 = GezilecekYer("Sümela Manastırı","Manastır","Trabzon'da bir manastır.",null,R.drawable.sumela_manastiri,OncelikDurumu.DUSUK)
        gezilecekYerList.add(gezilecekYer4)
         */


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

        val fragments =
            mutableListOf(
                GezileceklerFragment(),
               GezdiklerimFragment(),

            )

        viewPagerAdapter2 = ViewPagerAdapter2(supportFragmentManager, fragments, lifecycle)
        binding.viewPager.adapter = viewPagerAdapter2

        /*
        val adapter = ViewPagerAdapter(this)
        adapter.fragmentEkle(GezileceklerFragment())
        adapter.fragmentEkle(GezdiklerimFragment())

        binding.viewPager.adapter = adapter

         */
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

    class ViewPagerAdapter2(
        fragmentManager: FragmentManager, var fragments: MutableList<Fragment>, lifecycle: Lifecycle
    ) : FragmentStateAdapter(fragmentManager, lifecycle) {
        override fun getItemCount(): Int {
            return fragments.size
        }
        override fun createFragment(position: Int): Fragment {
            return fragments[position]
        }

        fun add(index: Int, fragment: Fragment) {
            fragments.add(index, fragment)
            notifyItemChanged(index)
        }

        fun refreshFragment(index: Int, fragment: Fragment) {
            fragments[index] = fragment
            notifyItemChanged(index)
        }

        fun remove(index: Int) {
            fragments.removeAt(index)
            notifyItemChanged(index)
        }

        override fun getItemId(position: Int): Long {
            return fragments[position].hashCode().toLong()
        }

        override fun containsItem(itemId: Long): Boolean {
            return fragments.find { it.hashCode().toLong() == itemId } != null
        }
    }

}










