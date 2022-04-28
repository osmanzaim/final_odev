package com.example.final_odev.View.viewholder

import android.content.Context
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.final_odev.R
import com.example.final_odev.View.DbBitmapUtility


class YerEklemeViewHolder(itemView : View,var context:Context,var listSize:Int, var deleteItem : (position : Int) -> Unit, var addPhoto :(position : Int)-> Unit) : RecyclerView.ViewHolder(itemView) {

    var yerEkleImageView : ImageView
    var imageButtonSil : ImageButton

    init {
        yerEkleImageView = itemView.findViewById(R.id.yerEkleImageView)
        imageButtonSil = itemView.findViewById(R.id.imageButtonSil)
        imageButtonSil.setOnClickListener() {
            deleteItem(adapterPosition)
        }

        itemView.setOnClickListener {
            if(adapterPosition == (listSize-1)) {
                addPhoto(adapterPosition+1)
            }
        }
    }

    fun bindData(fotoByteArray : ByteArray, isLast : Boolean) {

        var bitmap = DbBitmapUtility().getImage(fotoByteArray)

        yerEkleImageView.setImageBitmap(bitmap)
        imageButtonSil.setImageResource(R.drawable.ic_baseline_delete_24)
        if(isLast) {
            imageButtonSil.visibility = View.GONE
            yerEkleImageView.scaleType = ImageView.ScaleType.CENTER
        }

    }

}