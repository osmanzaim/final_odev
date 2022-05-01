package com.example.final_odev.View.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.final_odev.View.views.GezilecekYer
import com.example.final_odev.View.viewholder.CardViewHolder
import com.example.final_odev.databinding.CardItemBinding

class CardAdapter(val gezilecekYerler:List<GezilecekYer>,val gelenFragment:String, var f:(GezilecekYer)->Unit, var context:Context) : RecyclerView.Adapter<CardViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val from = LayoutInflater.from(parent.context)
        val binding = CardItemBinding.inflate(from,parent,false)
        return CardViewHolder(binding, f)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        holder.bindGezilecekYer(gezilecekYerler[position], gelenFragment, context)
    }

    override fun getItemCount(): Int {
        return gezilecekYerler.size
    }
}