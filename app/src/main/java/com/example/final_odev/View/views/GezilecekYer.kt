package com.example.final_odev.View.views

import com.example.final_odev.R

var gezilecekYerList = mutableListOf<GezilecekYer>()


//kapak fotoğrafı degiskeni eklendi
class GezilecekYer(

    var yerAdi: String,
    var kisaTanim: String,
    var aciklama: String? = null,
    var imageList: List<String>?,
    var kapakFotografi:ByteArray,
    var oncelik: OncelikDurumu,
    var ziyaretTarihi:String?=null,
    var flag:Int ?= null,
    var id: Int?

) {


}