package com.example.final_odev.data

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.example.final_odev.View.views.Fotograf
import com.example.final_odev.View.views.GezilecekYer
import com.example.final_odev.View.views.OncelikDurumu
import com.example.final_odev.View.views.Ziyaret

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

    fun fotografEkle(fotograf: Fotograf):Long{
        val cv = ContentValues()

        cv.put("FotoByteArray",fotograf.fotoByteArray)
        cv.put("YerAdi", fotograf.yerAdi)
        cv.put("ZiyaretAdi", fotograf.ziyaretAdi)
        cv.put("ZiyaretFK", fotograf.ZiyaretId)
        cv.put("YerFK", fotograf.yerFK)

        open()
        val etkilenenKayit = GeziDatabase!!.insert("Fotograf",null,cv)
        close()

        return etkilenenKayit

    }
    private fun fotografFKyeGore(fk:Int) : Cursor{
        val sorgu = "Select * from Fotograf WHERE YerFK=$fk"
        return GeziDatabase!!.rawQuery(sorgu,null)
    }
    @SuppressLint("Range")
    fun fotograflariFkyeGetir(id:Int): ArrayList<Fotograf>{
        val fotografList = ArrayList<Fotograf>()
        var fotograf : Fotograf

        open()
        var c : Cursor = fotografFKyeGore(id)
        if(c.moveToFirst()){
            do{
                var id  = c.getInt(0)
                var FotoByteArray = c.getBlob(c.getColumnIndex("FotoByteArray"))
                var yerAdi =  c.getString(c.getColumnIndex("YerAdi"))
                var ziyaretAdi = c.getString(c.getColumnIndex("ZiyaretAdi"))
                var ziyaretFK = c.getInt(c.getColumnIndex("ZiyaretFK"))
                var yerFk = c.getInt(c.getColumnIndex("YerFK"))

                fotograf = Fotograf(id,FotoByteArray,ziyaretFK,yerFk)
                fotograf.yerAdi = yerAdi
                fotograf.ziyaretAdi = ziyaretAdi
                fotografList.add(fotograf)

            }while (c.moveToNext())

        }

        close()

        return fotografList
    }






    private fun fotografZiyaretAdinaGore(ziyaretAdiveTarihi:String) : Cursor{
        val sorgu = "Select * from Fotograf WHERE ZiyaretAdi='$ziyaretAdiveTarihi'"
        return GeziDatabase!!.rawQuery(sorgu,null)
    }

    @SuppressLint("Range")
    fun fotograflariZiyareteGoreGetir(ziyaretAdiveTarihi:String): ArrayList<Fotograf>{
        val fotografList = ArrayList<Fotograf>()
        var fotograf : Fotograf

        open()
        var c : Cursor = fotografZiyaretAdinaGore(ziyaretAdiveTarihi)
        if(c.moveToFirst()){
            do{
                var id  = c.getInt(0)
                var FotoByteArray = c.getBlob(c.getColumnIndex("FotoByteArray"))
                var yerAdi =  c.getString(c.getColumnIndex("YerAdi"))
                var ziyaretAdi = c.getString(c.getColumnIndex("ZiyaretAdi"))
                var ziyaretFK = c.getInt(c.getColumnIndex("ZiyaretFK"))
                var yerFk = c.getInt(c.getColumnIndex("YerFK"))

                fotograf = Fotograf(id,FotoByteArray,ziyaretFK,yerFk)
                fotograf.yerAdi = yerAdi
                fotograf.ziyaretAdi = ziyaretAdi
                fotografList.add(fotograf)

            }while (c.moveToNext())

        }

        close()

        return fotografList
    }

    private fun fotografYereGore(yerAdi:String):Cursor{
        val sorgu = "Select * from Fotograf WHERE YerAdi='$yerAdi'"
        return GeziDatabase!!.rawQuery(sorgu,null)
    }

    @SuppressLint("Range")
    fun fotografiYereGoreGetir(yerAdi:String):ArrayList<Fotograf>{
        val fotografList = ArrayList<Fotograf>()
        var fotograf : Fotograf

        open()
        var c : Cursor = fotografYereGore(yerAdi)


        if(c.moveToFirst()){
            do{
                var id  = c.getInt(0)
                var FotoByteArray = c.getBlob(c.getColumnIndex("FotoByteArray"))
                var yerAdi =  c.getString(c.getColumnIndex("YerAdi"))
                var ziyaretFK = c.getInt(c.getColumnIndex("ZiyaretFK"))
                var yerFk = c.getInt(c.getColumnIndex("YerFK"))

                fotograf = Fotograf(id,FotoByteArray,ziyaretFK,yerFk)
                fotograf.yerAdi = yerAdi
                fotografList.add(fotograf)

            }while (c.moveToNext())

        }

        close()

        return fotografList
    }


    fun gezilecekYerEkle(gezilecekYer : GezilecekYer) : Long{
        val cv = ContentValues()
        cv.put("YerAdi",gezilecekYer.yerAdi)
        cv.put("KisaTanim",gezilecekYer.kisaTanim)
        cv.put("Flag",gezilecekYer.flag)
        cv.put("Aciklama", gezilecekYer.aciklama)
        cv.put("OncelikDurumu", gezilecekYer.oncelik.toString())
        cv.put("KapakFotografi", gezilecekYer.kapakFotografi)

        open()
        val etkilenenKayit = GeziDatabase!!.insert("GezilecekYer",null,cv)
        close()

        return etkilenenKayit
    }


    fun ziyaretEkle(ziyaret: Ziyaret) : Long {
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
    private fun ozelZiyaret(ziyaretTarihi:String,ziyaretAciklamasi:String, gezilecekYerId:Int):Cursor{

        val sorgu = "Select * from Ziyaret WHERE ZiyaretTarihi = '$ziyaretTarihi' AND Aciklama = '$ziyaretAciklamasi' AND GezilecekYerFK = $gezilecekYerId"
        return GeziDatabase!!.rawQuery(sorgu,null)

    }

    @SuppressLint("Range")
    fun ziyaretiGetirOzel(ziyaretTarihi:String, ziyaretAciklamasi:String, gezilecekYerId:Int ):ArrayList<Ziyaret>{
        val ziyaretList = ArrayList<Ziyaret>()
        var ziyaret : Ziyaret
        open()
        var c : Cursor = ozelZiyaret(ziyaretTarihi,ziyaretAciklamasi,gezilecekYerId)

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

    private fun flagaGore(flag:Int) : Cursor { // bu dışarıdan çağrılmayacak
        val sorgu = "Select * from GezilecekYer WHERE Flag = $flag"

        return GeziDatabase!!.rawQuery(sorgu,null)
    }

    private fun flagUpdate(flag:Int, id:Int) : Cursor { // bu dışarıdan çağrılmayacak
        val sorgu = "UPDATE GezilecekYer SET Flag = $flag WHERE ID=$id"
        //GeziDatabase!!.execSQL(sorgu)
        return GeziDatabase!!.rawQuery(sorgu,null)
    }

    @SuppressLint("Range")
    fun flagUpdateEt(flag:Int, id:Int):Int {
        val cv = ContentValues()
        cv.put("Flag",flag)

        open()
        val etkilenenKayit = GeziDatabase!!.update("GezilecekYer",cv,"id= ?", arrayOf<String>(id.toString()))
        close()

        return etkilenenKayit

    }




    @SuppressLint("Range")
    fun flagaGoreGetir(flag:Int) :ArrayList<GezilecekYer> {
        val gezilecekYerList = ArrayList<GezilecekYer>()
        var gezilecekYer : GezilecekYer

        open()
        var c : Cursor = flagaGore(flag)


        if(c.moveToFirst()){
            do{
                var id  = c.getInt(0)
                var yerAdi = c.getString(c.getColumnIndex("YerAdi"))
                var kisaTanim =  c.getString(c.getColumnIndex("KisaTanim"))
                var aciklama =  c.getString(c.getColumnIndex("Aciklama"))
                var oncelik = c.getString(c.getColumnIndex("OncelikDurumu"))
                var flag = c.getInt(c.getColumnIndex("Flag"))
                var kapakFotografi = c.getBlob(c.getColumnIndex("KapakFotografi"))
                var oncelikDurumu : OncelikDurumu
                if(oncelik == OncelikDurumu.DUSUK.toString()){
                    oncelikDurumu = OncelikDurumu.DUSUK
                }else if(oncelik == OncelikDurumu.ORTA.toString()){
                    oncelikDurumu = OncelikDurumu.ORTA
                }else{
                    oncelikDurumu = OncelikDurumu.YUKSEK
                }

                gezilecekYer = GezilecekYer(yerAdi,kisaTanim,aciklama,null,
                    kapakFotografi,oncelikDurumu,id=id, flag= flag)
                gezilecekYerList.add(gezilecekYer)

            }while (c.moveToNext())

        }

        close()

        return gezilecekYerList
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
                var flag = c.getInt(c.getColumnIndex("Flag"))
                var kapakFotografi = c.getBlob(c.getColumnIndex("KapakFotografi"))
                var oncelikDurumu : OncelikDurumu
                if(oncelik == OncelikDurumu.DUSUK.toString()){
                    oncelikDurumu = OncelikDurumu.DUSUK
                }else if(oncelik == OncelikDurumu.ORTA.toString()){
                    oncelikDurumu = OncelikDurumu.ORTA
                }else{
                    oncelikDurumu = OncelikDurumu.YUKSEK
                }

                gezilecekYer = GezilecekYer(yerAdi,kisaTanim,aciklama,null,
                    kapakFotografi,oncelikDurumu,id=id, flag= flag)
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
            var flag = c.getInt(c.getColumnIndex("Flag"))
            var kapakFotografi = c.getBlob(c.getColumnIndex("KapakFotografi"))
            var oncelikDurumu : OncelikDurumu
            if(oncelik == OncelikDurumu.DUSUK.toString()){
                oncelikDurumu = OncelikDurumu.DUSUK
            }else if(oncelik == OncelikDurumu.ORTA.toString()){
                oncelikDurumu = OncelikDurumu.ORTA
            }else{
                oncelikDurumu = OncelikDurumu.YUKSEK
            }

            gezilecekYer = GezilecekYer(yerAdi,kisaTanim,aciklama,null,kapakFotografi,oncelikDurumu,id=id, flag=flag)

        }else{
           gezilecekYer = GezilecekYer("0","",null,null, byteArrayOf(), OncelikDurumu.DUSUK,id=-1)
        }
        close()
        return gezilecekYer

    }



}