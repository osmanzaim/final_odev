package com.example.final_odev.View.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.final_odev.View.Ziyaret
import com.example.final_odev.View.viewholder.CardViewHolder
import com.example.final_odev.View.viewholder.ZiyaretViewHolder
import com.example.final_odev.databinding.ZiyaretCardCellBinding

class ZiyaretAdapter(val ziyaretList: List<Ziyaret>, ) : RecyclerView.Adapter<ZiyaretViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ZiyaretViewHolder {
        val from = LayoutInflater.from(parent.context)
        val binding = ZiyaretCardCellBinding.inflate(from,parent,false)
        return ZiyaretViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ZiyaretViewHolder, position: Int) {
        holder.bindZiyaret(ziyaretList[position])
    }

    override fun getItemCount(): Int {
        return ziyaretList.size
    }


}