package com.example.final_odev.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.example.final_odev.R
import com.example.final_odev.View.Adapter.CardAdapter
import com.example.final_odev.View.gezilecekYerList
import com.example.final_odev.databinding.FragmentGezileceklerBinding

class GezileceklerFragment : Fragment() {

    lateinit var binding: FragmentGezileceklerBinding



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGezileceklerBinding.inflate(inflater,container,false)
        rvHazirla()






        return binding.root
    }



    fun rvHazirla() {
        binding.rvGezilecekYerler.apply{
            layoutManager = GridLayoutManager(requireActivity().applicationContext,1)
            adapter = CardAdapter(gezilecekYerList)
        }
    }


}