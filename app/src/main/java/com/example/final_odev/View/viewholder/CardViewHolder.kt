package com.example.final_odev.View.viewholder

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.final_odev.R
import com.example.final_odev.View.views.DbBitmapUtility
import com.example.final_odev.View.views.GezilecekYer
import com.example.final_odev.View.views.OncelikDurumu
import com.example.final_odev.databinding.CardItemBinding

class CardViewHolder(val cardItemBinding:CardItemBinding, var listener:(GezilecekYer)->Unit) : RecyclerView.ViewHolder(cardItemBinding.root) {
    lateinit var gezilecekYerObjesi : GezilecekYer





    fun bindGezilecekYer(gezilecekYer:GezilecekYer, gelenFragment:String, context:Context) {
        gezilecekYerObjesi = gezilecekYer
            // GezileceklerFragment'ten geldiyse, öncelik durumunu göster, ziyaret edilmiş ise onun yerine tarihini göster.
        if(gelenFragment == "gezdigim") {
            cardItemBinding.imageViewOncelik.visibility = View.GONE
            cardItemBinding.textViewTarih.visibility = View.VISIBLE
            //val encodedString: String = Base64.getEncoder().encodeToString(gezilecekYer.kapakFotografi.toByteArray())

            var image = DbBitmapUtility().getImage(gezilecekYer.kapakFotografi)



            cardItemBinding.imageViewKapakFotografi.setImageBitmap(image)



            cardItemBinding.tvKisaAciklama.text = gezilecekYer.aciklama
            cardItemBinding.tvKisaTanim.text = gezilecekYer.kisaTanim
            cardItemBinding.tvYerAdi.text  = gezilecekYer.yerAdi
            cardItemBinding.textViewTarih.text = gezilecekYer.ziyaretTarihi


        }else{
            cardItemBinding.imageViewOncelik.visibility = View.VISIBLE
            cardItemBinding.textViewTarih.visibility = View.GONE

            var image = DbBitmapUtility().getImage(gezilecekYer.kapakFotografi)

            cardItemBinding.imageViewKapakFotografi.setImageBitmap(image)
            //cardItemBinding.imageViewKapakFotografi.setImageResource(gezilecekYer.kapakFotografi)
            //cardItemBinding.imageViewKapakFotografi.setImageURI(uri)
            cardItemBinding.tvKisaAciklama.text = gezilecekYer.aciklama
            cardItemBinding.tvKisaTanim.text = gezilecekYer.kisaTanim
            cardItemBinding.tvYerAdi.text  = gezilecekYer.yerAdi

            var oncelik = gezilecekYer.oncelik
            if(oncelik == OncelikDurumu.DUSUK){
                cardItemBinding.imageViewOncelik.setImageResource(R.drawable.dusuk_oncelik_belirtec)
            }else if(oncelik == OncelikDurumu.ORTA){
                cardItemBinding.imageViewOncelik.setImageResource(R.drawable.orta_oncelik_belirtec)
            }else if(oncelik == OncelikDurumu.YUKSEK){
                cardItemBinding.imageViewOncelik.setImageResource(R.drawable.yuksek_oncelik_belirtec)
            }
        }




    }

    init{
        cardItemBinding.cardView.setOnClickListener {
            listener(gezilecekYerObjesi)
        }
    }



}