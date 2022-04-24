package com.example.final_odev.View.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.final_odev.View.GezilecekYer
import com.example.final_odev.View.viewholder.CardViewHolder
import com.example.final_odev.databinding.CardItemBinding

class CardAdapter(val gezilecekYerler:List<GezilecekYer>) : RecyclerView.Adapter<CardViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val from = LayoutInflater.from(parent.context)
        val binding = CardItemBinding.inflate(from,parent,false)
        return CardViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        holder.bindGezilecekYer(gezilecekYerler[position])
    }

    override fun getItemCount(): Int {
        return gezilecekYerler.size
    }
}