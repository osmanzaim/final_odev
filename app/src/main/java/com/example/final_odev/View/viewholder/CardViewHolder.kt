package com.example.final_odev.View.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.example.final_odev.R
import com.example.final_odev.View.GezilecekYer
import com.example.final_odev.View.OncelikDurumu
import com.example.final_odev.databinding.CardItemBinding

class CardViewHolder(val cardItemBinding:CardItemBinding) : RecyclerView.ViewHolder(cardItemBinding.root) {

    fun bindGezilecekYer(gezilecekYer:GezilecekYer) {
        cardItemBinding.imageViewKapakFotografi.setImageResource(gezilecekYer.kapakFotografi)
        cardItemBinding.tvKisaAciklama.text = gezilecekYer.aciklama
        cardItemBinding.tvKisaTanim.text = gezilecekYer.kisaTanim
        cardItemBinding.tvYerAdi.text  = gezilecekYer.yerAdi

        var oncelik = gezilecekYer.oncelik
        if(oncelik == OncelikDurumu.DUSUK){
            cardItemBinding.imageViewOncelik.setImageResource(R.drawable.dusuk_oncelik_belirtec)
        }else if(oncelik == OncelikDurumu.ORTA){
            cardItemBinding.imageViewOncelik.setImageResource(R.drawable.orta_oncelik_belirtec)
        }else{
            cardItemBinding.imageViewOncelik.setImageResource(R.drawable.yuksek_oncelik_belirtec)
        }
    }

}