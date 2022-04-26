package com.example.final_odev.fragments

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.recyclerview.widget.GridLayoutManager
import com.example.final_odev.DetayFragment
import com.example.final_odev.R
import com.example.final_odev.View.Adapter.CardAdapter
import com.example.final_odev.View.GezilecekYer
import com.example.final_odev.View.gezilecekYerList
import com.example.final_odev.databinding.FragmentGezileceklerBinding
import com.example.final_odev.viewmodel.GezilecekYerLogic

class GezileceklerFragment : Fragment() {

    lateinit var binding: FragmentGezileceklerBinding



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onResume() {
        super.onResume()
        binding.rvGezilecekYerler.adapter!!.notifyDataSetChanged()


    }




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGezileceklerBinding.inflate(inflater,container,false)
        rvHazirla()

        //Toast.makeText(requireContext(), gezilecekYerList.size.toString(), Toast.LENGTH_LONG).show()
        if(gezilecekYerList.size>0){
            Toast.makeText(requireContext(), gezilecekYerList[0].oncelik.toString(),Toast.LENGTH_LONG).show()
        }

        return binding.root
    }



    fun rvHazirla() {
        binding.rvGezilecekYerler.apply{
            layoutManager = GridLayoutManager(requireActivity().applicationContext,1)
            gezilecekYerList = GezilecekYerLogic.tumGezilecekYerleriGetir(requireContext())
            adapter = CardAdapter(gezilecekYerList,"gezilecek", ::cardClickListener)
        }
    }


    fun cardClickListener(gezilecekYer:GezilecekYer) {
        //Toast.makeText(requireContext(),gezilecekYer.aciklama,Toast.LENGTH_LONG).show()
        //nesneyi aldık.

        val intent = Intent (activity, DetayActivity::class.java)
        intent.putExtra("id",gezilecekYer.id)
        startActivity(intent)

        /*
        val transaction = requireActivity().supportFragmentManager.beginTransaction()
        transaction.replace(R.id.viewPager, DetayFragment())
        transaction.disallowAddToBackStack()
        transaction.commit()

         */

        //Navigation.findNavController(binding.root).navigate(R.id.action_gezileceklerFragment_to_detayFragment)
        //findNavController().navigate(R.id.action_gezileceklerFragment_to_detayFragment)
        //val navHostFragment = supportFragmentManager.findFragmentById(R.id.action_gezileceklerFragment_to_detayFragment) as NavHostFragment
        //val navController = navHostFragment.navController
    }

}


