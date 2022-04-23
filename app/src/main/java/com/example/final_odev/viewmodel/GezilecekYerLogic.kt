package com.example.final_odev.viewmodel

import android.content.Context
import com.example.final_odev.View.GezilecekYer
import com.example.final_odev.data.Operation

class GezilecekYerLogic {
    companion object{
        fun ekle(context: Context, gezilecekYer: GezilecekYer){
            val operation = Operation(context)
            operation.gezilecekYerEkle(gezilecekYer)

        }


        fun tumGezilecekYerleriGetir(context: Context): ArrayList<GezilecekYer>{
            return Operation(context).gezilecekYerleriGetir()
        }
    }
}