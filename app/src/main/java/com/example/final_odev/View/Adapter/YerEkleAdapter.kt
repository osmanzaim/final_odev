package com.example.final_odev.View.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.final_odev.R
import com.example.final_odev.View.viewholder.YerEklemeViewHolder


class YerEkleAdapter(var context : Context, var resimList : ArrayList<ByteArray>, var deleteItem : (position : Int) -> Unit, var addPhoto :(position : Int)-> Unit) : RecyclerView.Adapter<YerEklemeViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): YerEklemeViewHolder {
        var view = LayoutInflater.from(context).inflate(R.layout.rv_fotograflar, parent, false)
        return YerEklemeViewHolder(view,context,resimList.size,deleteItem, addPhoto)

    }


    override fun onBindViewHolder(holder: YerEklemeViewHolder, position: Int) {
        var isLast = (resimList.size-1) == position
        holder.bindData(resimList.get(position), isLast)
    }

    override fun getItemCount(): Int {
        return resimList.size
    }
}