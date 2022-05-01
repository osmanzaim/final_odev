package com.example.final_odev.View.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.example.final_odev.View.views.Ziyaret
import com.example.final_odev.databinding.ZiyaretCardCellBinding

class ZiyaretViewHolder(var ziyaretCardCellBinding: ZiyaretCardCellBinding):RecyclerView.ViewHolder(ziyaretCardCellBinding.root) {


    fun bindZiyaret(ziyaret: Ziyaret) {
        ziyaretCardCellBinding.tvAciklama.text = ziyaret.aciklama
        ziyaretCardCellBinding.tvTarih.text = ziyaret.ziyaretTarihi
    }
}