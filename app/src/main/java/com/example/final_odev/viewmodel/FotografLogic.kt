package com.example.final_odev.viewmodel

import android.content.Context
import com.example.final_odev.View.Fotograf
import com.example.final_odev.data.Operation

class FotografLogic {

    companion object{
        fun ekle(context: Context, fotograf:Fotograf){
            val operation = Operation(context)
            operation.fotografEkle(fotograf)
        }


        fun yerAdinaGoreGetir(context: Context, yerAdi:String):ArrayList<Fotograf>{
            val operation = Operation(context)
            return operation.fotografiYereGoreGetir(yerAdi)
        }

        fun ziyaretAdinaGoreGetir(context: Context, ziyaretAdi:String):ArrayList<Fotograf>{
            val operation = Operation(context)
            return operation.fotograflariZiyareteGoreGetir(ziyaretAdi)
        }


        fun fkyeGore(context:Context, fk:Int):ArrayList<Fotograf>{
            val operation = Operation(context)
            return operation.fotograflariFkyeGetir(fk)
        }


    }
}