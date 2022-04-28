package com.example.final_odev.View.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.final_odev.R
import com.example.final_odev.View.Fotograf
import com.example.final_odev.View.viewholder.CardViewHolder
import com.example.final_odev.View.viewholder.SliderViewHolder
import com.example.final_odev.databinding.CardItemBinding
import com.example.final_odev.databinding.RvFotoSliderBinding

class SliderAdapter(var context: Context, var fotoList : ArrayList<Fotograf>,var tarih:String?, var fragment : String, var clickSliderGeri:() -> Unit, var clickSliderIleri:()-> Unit): RecyclerView.Adapter<SliderViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SliderViewHolder {


        val from = LayoutInflater.from(parent.context)
        val binding = RvFotoSliderBinding.inflate(from,parent,false)
        return SliderViewHolder(binding,fragment,clickSliderGeri,clickSliderIleri)
    }

    override fun onBindViewHolder(holder: SliderViewHolder, position: Int) {
        holder.bindData(fotoList.get(position), tarih)
    }

    override fun getItemCount(): Int {
        return fotoList.size
    }
}