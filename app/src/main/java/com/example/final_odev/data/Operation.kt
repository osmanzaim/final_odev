package com.example.final_odev.data

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.example.final_odev.View.GezilecekYer
import com.example.final_odev.View.OncelikDurumu
import com.example.final_odev.View.Ziyaret

class Operation(context: Context) {
    var GeziDatabase : SQLiteDatabase?= null
    var dbOpenHelper : DatabaseOpenHelper

    init{
        dbOpenHelper = DatabaseOpenHelper(context,"GeziDb", null,1)
    }

    fun open() {
        GeziDatabase = dbOpenHelper.writableDatabase  // yazmak üzere aç demek, hem yazıp hem okuyabilirsin. readableDatabase ise sadece okuyaiblir.
    }

    fun close() {
        if(GeziDatabase != null && GeziDatabase!!.isOpen) {
            GeziDatabase!!.close()
        }
    }


    fun gezilecekYerEkle(gezilecekYer : GezilecekYer) : Long{
        val cv = ContentValues()
        cv.put("YerAdi",gezilecekYer.yerAdi)
        cv.put("KisaTanim",gezilecekYer.kisaTanim)
        cv.put("Aciklama", gezilecekYer.aciklama)
        cv.put("OncelikDurumu", gezilecekYer.oncelik.toString())
        cv.put("KapakFotografi", gezilecekYer.kapakFotografi)

        open()
        val etkilenenKayit = GeziDatabase!!.insert("GezilecekYer",null,cv)
        close()

        return etkilenenKayit
    }


    fun ziyaretEkle(ziyaret:Ziyaret) : Long {
        val cv = ContentValues()
        cv.put("ZiyaretTarihi", ziyaret.ziyaretTarihi)
        cv.put("Aciklama", ziyaret.aciklama)
        cv.put("GezilecekYerFK",ziyaret.gezilecekYerFK)

        open()
        val etkilenenKayit = GeziDatabase!!.insert("Ziyaret",null,cv)
        close()

        return etkilenenKayit

    }

    private fun fkyeGoreZiyaret(foreignKey:Int):Cursor{
        val sorgu = "Select * from Ziyaret WHERE GezilecekYerFK = $foreignKey"
        return GeziDatabase!!.rawQuery(sorgu,null)
    }

    @SuppressLint("Range")
    fun fkyeGoreZiyaretGetir(foreignKey: Int) : ArrayList<Ziyaret>{
        val ziyaretList = ArrayList<Ziyaret>()
        var ziyaret : Ziyaret

        open()
        var c : Cursor = fkyeGoreZiyaret(foreignKey)


        if(c.moveToFirst()){
            do{
                var id  = c.getInt(0)
                var ziyaretTarihi = c.getString(c.getColumnIndex("ZiyaretTarihi"))
                var aciklama =  c.getString(c.getColumnIndex("Aciklama"))
                var gezilecekYerFK = c.getInt(c.getColumnIndex("GezilecekYerFK"))

                ziyaret = Ziyaret(id,ziyaretTarihi,aciklama,gezilecekYerFK)
                ziyaretList.add(ziyaret)

            }while (c.moveToNext())

        }

        close()

        return ziyaretList

    }


    private fun tumZiyaretler():Cursor{
        val sorgu = "Select * from Ziyaret"
        return GeziDatabase!!.rawQuery(sorgu,null)

    }

    @SuppressLint("Range")
    fun tumZiyaretleriGetir() : ArrayList<Ziyaret>{
        val ziyaretList = ArrayList<Ziyaret>()
        var ziyaret : Ziyaret

        open()
        var c : Cursor = tumZiyaretler()

        if(c.moveToFirst()){
            do{
                var id  = c.getInt(0)
                var ziyaretTarihi = c.getString(c.getColumnIndex("ZiyaretTarihi"))
                var aciklama =  c.getString(c.getColumnIndex("Aciklama"))
                var gezilecekYerFK = c.getInt(c.getColumnIndex("GezilecekYerFK"))

                ziyaret = Ziyaret(id,ziyaretTarihi,aciklama,gezilecekYerFK)
                ziyaretList.add(ziyaret)

            }while (c.moveToNext())

        }

        close()

        return ziyaretList
    }



    private fun tumGezilecekYerleriGetir() : Cursor { // bu dışarıdan çağrılmayacak
        val sorgu = "Select * from GezilecekYer"

        return GeziDatabase!!.rawQuery(sorgu,null)
    }

/*
    private fun tumZiyaretleriGetir() : Cursor { // bu dışarıdan çağrılmayacak
        val sorgu = "Select * from Ziyaret"

        return GeziDatabase!!.rawQuery(sorgu,null)

    }

 */

    // tüm gezilecek yerleri getirir.
    @SuppressLint("Range")
    fun gezilecekYerleriGetir() :ArrayList<GezilecekYer> {
        val gezilecekYerList = ArrayList<GezilecekYer>()
        var gezilecekYer : GezilecekYer

        open()
        var c : Cursor = tumGezilecekYerleriGetir()


        if(c.moveToFirst()){
            do{
                var id  = c.getInt(0)
                var yerAdi = c.getString(c.getColumnIndex("YerAdi"))
                var kisaTanim =  c.getString(c.getColumnIndex("KisaTanim"))
                var aciklama =  c.getString(c.getColumnIndex("Aciklama"))
                var oncelik = c.getString(c.getColumnIndex("OncelikDurumu"))
                var kapakFotografi = c.getInt(c.getColumnIndex("KapakFotografi"))
                var oncelikDurumu : OncelikDurumu
                if(oncelik == OncelikDurumu.DUSUK.toString()){
                    oncelikDurumu = OncelikDurumu.DUSUK
                }else if(oncelik == OncelikDurumu.ORTA.toString()){
                    oncelikDurumu = OncelikDurumu.ORTA
                }else{
                    oncelikDurumu = OncelikDurumu.YUKSEK
                }

                gezilecekYer = GezilecekYer(yerAdi,kisaTanim,aciklama,null,kapakFotografi,oncelikDurumu,id=id)
                gezilecekYerList.add(gezilecekYer)

            }while (c.moveToNext())

        }

        close()

        return gezilecekYerList
    }


    private fun idYeGoreGezilecekYer(id:Int) : Cursor {
        val sorgu = "Select * from GezilecekYer WHERE Id=$id"

        var selectionArgs = arrayOf(id)
        return GeziDatabase!!.rawQuery(sorgu,null)
    }
    @SuppressLint("Range")
    fun idyeGoreGezilecekYerGetir(id:Int) : GezilecekYer{
        var gezilecekYer : GezilecekYer

        open()
        val c:Cursor = idYeGoreGezilecekYer(id)

        if(c.moveToFirst()){
            var id  = c.getInt(0)
            var yerAdi = c.getString(c.getColumnIndex("YerAdi"))
            var kisaTanim =  c.getString(c.getColumnIndex("KisaTanim"))
            var aciklama =  c.getString(c.getColumnIndex("Aciklama"))
            var oncelik = c.getString(c.getColumnIndex("OncelikDurumu"))
            var kapakFotografi = c.getInt(c.getColumnIndex("KapakFotografi"))
            var oncelikDurumu : OncelikDurumu
            if(oncelik == OncelikDurumu.DUSUK.toString()){
                oncelikDurumu = OncelikDurumu.DUSUK
            }else if(oncelik == OncelikDurumu.ORTA.toString()){
                oncelikDurumu = OncelikDurumu.ORTA
            }else{
                oncelikDurumu = OncelikDurumu.YUKSEK
            }

            gezilecekYer = GezilecekYer(yerAdi,kisaTanim,aciklama,null,kapakFotografi,oncelikDurumu,id=id)

        }else{
           gezilecekYer = GezilecekYer("0","",null,null,0,OncelikDurumu.DUSUK,id=-1)
        }
        close()
        return gezilecekYer

    }



}