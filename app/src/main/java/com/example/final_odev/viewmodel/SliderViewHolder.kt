package com.example.final_odev.View.viewholder

import android.media.Image
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.final_odev.R
import com.example.final_odev.View.DbBitmapUtility
import com.example.final_odev.View.Fotograf
import com.example.final_odev.databinding.CardItemBinding
import com.example.final_odev.databinding.RvFotoSliderBinding

class SliderViewHolder(val cardItemBinding: RvFotoSliderBinding, var fragment:String, clickSliderGeri:() -> Unit, clickSliderIleri:()-> Unit) : RecyclerView.ViewHolder(cardItemBinding.root) {



    init {


        if(fragment == "gezilecekler"){
            cardItemBinding.cardViewTarih.visibility = View.GONE
            cardItemBinding.sliderTarih.visibility = View.GONE
        }else{
            cardItemBinding.cardViewTarih.visibility = View.VISIBLE
            cardItemBinding.sliderTarih.visibility = View.VISIBLE
        }

        cardItemBinding.sliderGeri.setOnClickListener {
            clickSliderGeri()
        }


        cardItemBinding.sliderIleri.setOnClickListener {
            clickSliderIleri()
        }
    }

    fun bindData(fotograf: Fotograf, fotoTarih:String?) {

        var fotoByteArray = fotograf.fotoByteArray
        var bitmap = DbBitmapUtility().getImage(fotoByteArray)

        cardItemBinding.sliderImageView.setImageBitmap(bitmap)

        cardItemBinding.sliderTarih.setText(fotoTarih)
    }


}