package com.example.final_odev.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.example.final_odev.View.Adapter.CardAdapter
import com.example.final_odev.View.views.GezilecekYer
import com.example.final_odev.View.views.Ziyaret
import com.example.final_odev.View.activities.DetayActivity
import com.example.final_odev.databinding.FragmentGezdiklerimBinding
import com.example.final_odev.viewmodel.GezilecekYerLogic
import com.example.final_odev.viewmodel.ZiyaretLogic

class GezdiklerimFragment : Fragment() {

    companion object{
        var gezdigimYerlerListesi = arrayListOf<GezilecekYer>()
        var ziyaretList = arrayListOf<Ziyaret>()

    }
    lateinit var binding:FragmentGezdiklerimBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onResume() {
        super.onResume()
        binding.rvGezdigimYerler.adapter = CardAdapter(gezdigimYerlerListesi,"gezdigim", ::cardClickListener,requireContext())
        yerleriBul()
        rvHazirla()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGezdiklerimBinding.inflate(inflater,container,false)
        yerleriBul()
        rvHazirla()





        return binding.root
    }

    fun yerleriBul() {

        gezdigimYerlerListesi.clear()
        gezdigimYerlerListesi = GezilecekYerLogic.flagaGoreGetir(requireContext(),1)


        /*
          for(item in ziyaretListesi){
            var gezdigimYer = GezilecekYerLogic.idyeGoreGetir(requireContext(),item.gezilecekYerFK)
            gezdigimYer.ziyaretTarihi = item.ziyaretTarihi
            gezdigimYerlerListesi.add(gezdigimYer)
        }

         */


    }


    fun gezdiklerimListesiDoldur() {

        var ziyaretListesi = ZiyaretLogic.tumunuGetir(requireContext())

        for (item in ziyaretListesi){

        }

        /*
        val gezilecekYer2 = GezilecekYer("Mençuna Şelalesi","Büyük bir şelale","Doğa içerisinde büyük bir şelale.",null,R.drawable.mencuna_selalesi,
            OncelikDurumu.ORTA,45645)
        gezdigimYerlerListesi.add(gezilecekYer2)
        val gezilecekYer3 = GezilecekYer("Sümela Manastırı","Manastır","Trabzon'da bir manastır.",null,R.drawable.sumela_manastiri,
            OncelikDurumu.DUSUK,346345)
        gezdigimYerlerListesi.add(gezilecekYer3)

         */
    }


     fun rvHazirla() {
            binding.rvGezdigimYerler.apply{
                layoutManager = GridLayoutManager(requireActivity().applicationContext,1)
                adapter = CardAdapter(gezdigimYerlerListesi,"gezdigim", ::cardClickListener,requireContext())
            }
        }



    fun cardClickListener(gezilecekYer:GezilecekYer) {

        val intent = Intent (activity, DetayActivity::class.java)

        val ziyaret = ZiyaretLogic.fkyeGoreGetir(requireContext(),gezilecekYer.id!!)
        ziyaretList = ziyaret

        intent.putExtra("id",gezilecekYer.id)
        intent.putExtra("tarih",ziyaret[0].ziyaretTarihi)
        intent.putExtra("geldigiFragment","asd")
        intent.putExtra("durum","gezdiklerim")
        startActivity(intent)

    }





}