package com.example.final_odev.View.ViewHolder



import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.final_odev.R
import com.example.final_odev.View.GezilecekYer

class GezilecekYerlerViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {

    var iv_gezilecekYerler : ImageView
    var tv_yerAdi : TextView
    var tv_yerKisaTanitim : TextView
    var tv_kisaAciklama : TextView

    init {
        iv_gezilecekYerler = itemView.findViewById(R.id.iv_gezilecekYerler)
        tv_yerAdi = itemView.findViewById(R.id.tv_yerAdi)
        tv_yerKisaTanitim = itemView.findViewById(R.id.tv_yerKisaTanitim)
        tv_kisaAciklama = itemView.findViewById(R.id.tv_kisaAciklama)
    }

    fun bindData(gezilecekYer : GezilecekYer) {
        iv_gezilecekYerler.setImageResource(R.drawable.aa)
        tv_yerAdi.setText(gezilecekYer.yerAdi)
        tv_yerKisaTanitim.setText(gezilecekYer.kisaTanim)
        tv_kisaAciklama.setText(gezilecekYer.aciklama)
    }

}