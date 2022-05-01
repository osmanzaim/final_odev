package com.example.final_odev.View.views

class Ziyaret(
    var id: Int?,
    var ziyaretTarihi: String,
    var aciklama: String,
    var gezilecekYerFK: Int
) {

    lateinit var fotografListesi : ArrayList<Fotograf>

}