package com.example.final_odev.View.views

import android.net.Uri
import java.util.*

var ziyaretFotograflari = arrayListOf<Fotograf>()

class Fotograf (var id:Int?, var fotoByteArray: ByteArray, var ZiyaretId:Int?, var yerFK:Int? ) {
    var yerAdi:String ?= null
    var ziyaretAdi:String ?= null


}