package com.example.final_odev.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.example.final_odev.R
import com.example.final_odev.View.Adapter.CardAdapter
import com.example.final_odev.View.GezilecekYer
import com.example.final_odev.View.OncelikDurumu
import com.example.final_odev.View.gezilecekYerList
import com.example.final_odev.databinding.FragmentGezdiklerimBinding

class GezdiklerimFragment : Fragment() {

    companion object{
        var gezdigimYerlerListesi = mutableListOf<GezilecekYer>()
    }
    lateinit var binding:FragmentGezdiklerimBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGezdiklerimBinding.inflate(inflater,container,false)
        gezdiklerimListesiDoldur()
        rvHazirla()





        return binding.root
    }


    fun gezdiklerimListesiDoldur() {
        val gezilecekYer2 = GezilecekYer("Mençuna Şelalesi","Büyük bir şelale","Doğa içerisinde büyük bir şelale.",null,R.drawable.mencuna_selalesi,
            OncelikDurumu.ORTA)
        gezdigimYerlerListesi.add(gezilecekYer2)
        val gezilecekYer3 = GezilecekYer("Sümela Manastırı","Manastır","Trabzon'da bir manastır.",null,R.drawable.sumela_manastiri,
            OncelikDurumu.DUSUK)
        gezdigimYerlerListesi.add(gezilecekYer3)
    }


     fun rvHazirla() {
            binding.rvGezdigimYerler.apply{
                layoutManager = GridLayoutManager(requireActivity().applicationContext,1)
                adapter = CardAdapter(gezdigimYerlerListesi,"gezdigim", ::cardClickListener)
            }
        }



    fun cardClickListener(gezilecekYer:GezilecekYer) {

    }




}