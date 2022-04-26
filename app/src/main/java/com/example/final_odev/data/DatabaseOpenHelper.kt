package com.example.final_odev.data

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseOpenHelper(context: Context, name:String, factory:SQLiteDatabase.CursorFactory?, version: Int) :SQLiteOpenHelper(context,name,factory,version) {
    override fun onCreate(p0: SQLiteDatabase) {
        val sorgu = "CREATE TABLE GezilecekYer(Id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, YerAdi TEXT, KisaTanim TEXT, Aciklama TEXT, KapakFotografi INTEGER, OncelikDurumu TEXT)"
        //ziyaret listesini yukarıdaki sorguya eklemedim, aşağıda ziyaret tablosunda foreign key var zaten oradan id si uygun olanları çekeceğiz.
        // fotograf listesi için de ayrı table oluşturup Foregin key ile tutacağız.
        val sorgu2 = "CREATE TABLE Ziyaret(Id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, ZiyaretTarihi TEXT, Aciklama TEXT, GezilecekYerFK INTEGER, FOREIGN KEY(GezilecekYerFK) REFERENCES GezilecekYer(Id))"

        p0.execSQL(sorgu)
        p0.execSQL(sorgu2)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("Not yet implemented")
    }
}