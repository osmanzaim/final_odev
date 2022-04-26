package com.example.final_odev.View

import com.example.final_odev.R

var gezilecekYerList = mutableListOf<GezilecekYer>()


//kapak fotoğrafı degiskeni eklendi
class GezilecekYer(

    var yerAdi: String,
    var kisaTanim: String,
    var aciklama: String? = null,
    var imageList: List<String>?,
    var kapakFotografi:Int,
    var oncelik: OncelikDurumu,
    var ziyaretTarihi:String?=null,
    var id: Int?
) {


}