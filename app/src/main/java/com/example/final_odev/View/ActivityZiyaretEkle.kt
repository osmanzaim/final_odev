package com.example.final_odev.View

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.final_odev.R
import com.example.final_odev.View.Adapter.YerEkleAdapter
import com.example.final_odev.databinding.ActivityZiyaretEkleBinding
import com.example.final_odev.viewmodel.ZiyaretLogic

class ActivityZiyaretEkle : AppCompatActivity() {
    lateinit var binding: ActivityZiyaretEkleBinding
    lateinit var imageList: ArrayList<Int>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityZiyaretEkleBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()

        btnZiyaretKaydetListener()

        //ziyaret ekle sayfasini devam ettir.

    }

    private fun btnZiyaretKaydetListener() {
        binding.btnZiyaretKaydet.setOnClickListener {

            var gezilecekYerId = intent.getIntExtra("gezilecekYerId",-1)
            var ziyaretTarihi:String = binding.ptZiyaretTarihi.text.toString()
            var ziyaretAciklamasi: String = binding.ptZiyaretAciklamasi.text.toString()

            var ziyaret = Ziyaret(null,ziyaretTarihi,ziyaretAciklamasi,gezilecekYerId)
            ZiyaretLogic.ekle(this,ziyaret)
            //veritabanına kaydedildi.

            var intent = Intent()
            setResult(RESULT_OK,intent)
            finish()

            //var ziyaretList = ZiyaretLogic.fkyeGoreGetir(this,gezilecekYerId)
            //Toast.makeText(this,ziyaretList.size.toString(),Toast.LENGTH_SHORT).show()

        }
    }


    fun init() {
        imageList = ArrayList<Int>()
        val lm = LinearLayoutManager(this)
        lm.orientation = LinearLayoutManager.HORIZONTAL
        binding.rvFotoEkle.layoutManager = lm

        //
        imageList.add(R.drawable.fotoekle)
        binding.rvFotoEkle.adapter = YerEkleAdapter(this,imageList!!, ::deleteItem, ::addPhoto)
    }

    fun deleteItem(position : Int) {
        // Fotoğraf Silme işlemi yapılacak
        Toast.makeText(this,"Silme", Toast.LENGTH_SHORT).show()

    }

    fun addPhoto(position : Int) {
        Toast.makeText(this,"Ekleme", Toast.LENGTH_SHORT).show()

        // Burada fotoğraf ekleme işlemi yapılacak. Tıklanıldığında bll ile
    }
}