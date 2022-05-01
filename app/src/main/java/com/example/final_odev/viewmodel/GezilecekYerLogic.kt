package com.example.final_odev.viewmodel

import android.content.Context
import com.example.final_odev.View.views.GezilecekYer
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


        fun idyeGoreGetir(context:Context, id:Int) : GezilecekYer{
            return Operation(context).idyeGoreGezilecekYerGetir(id)
        }



        fun flagaGoreGetir(context:Context,flag:Int):ArrayList<GezilecekYer>{
            val operation = Operation(context)
            return operation.flagaGoreGetir(flag)
        }

        fun flagUpdate(context:Context, flag:Int,id:Int){
            val operation = Operation(context)
             operation.flagUpdateEt(flag,id)
        }
    }
}