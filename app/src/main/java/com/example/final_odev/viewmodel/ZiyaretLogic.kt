package com.example.final_odev.viewmodel

import android.content.Context
import com.example.final_odev.View.views.Ziyaret
import com.example.final_odev.data.Operation

class ZiyaretLogic {

    companion object{

        fun ekle(context:Context, ziyaret: Ziyaret){
            Operation(context).ziyaretEkle(ziyaret)
        }

        fun tumunuGetir(context: Context):ArrayList<Ziyaret>{
            var operation = Operation(context)
            return operation.tumZiyaretleriGetir()
        }


        fun fkyeGoreGetir(context: Context, foreignKey:Int) : ArrayList<Ziyaret>{
            var operation = Operation(context)
            return operation.fkyeGoreZiyaretGetir(foreignKey)
        }


        fun ziyaretGetirOzel( context: Context, ziyaretTarihi:String, aciklama:String, id:Int) : Ziyaret
        {
            var operation = Operation(context)
            return operation.ziyaretiGetirOzel(ziyaretTarihi, aciklama,id)[0]
        }


    }
}