package com.example.final_odev.View

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.final_odev.R
import com.example.final_odev.View.Adapter.YerEkleAdapter
import com.example.final_odev.databinding.ActivityYerEkleBinding
import com.example.final_odev.viewmodel.GezilecekYerLogic


class ActivityYerEkle : AppCompatActivity() {
    lateinit var imageList: ArrayList<Int>
    lateinit var binding : ActivityYerEkleBinding
    var oncelikDurumu : String = "YUKSEK"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityYerEkleBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
        spinner()

        btnKaydetClickListener()



    }

    private fun btnKaydetClickListener() {
        binding.btnYerKaydet.setOnClickListener {
            var yerAdi = binding.etYerAdi.text.toString()
            var yerKisaTanim = binding.etYerKisaTanim.text.toString()
            var kisaAciklama = binding.etYerKisaAciklama.text.toString()
            var oncelik : OncelikDurumu
            val kapakFotografi : Int // Int-Uri-String halinde de fotoğrafları tutabiliriz.

            if(oncelikDurumu == OncelikDurumu.YUKSEK.toString()){
                oncelik = OncelikDurumu.YUKSEK
            }else if(oncelikDurumu == OncelikDurumu.ORTA.toString()){
                oncelik = OncelikDurumu.ORTA
            }else{
                oncelik = OncelikDurumu.DUSUK
            }

            if(imageList.size == 1) {
                kapakFotografi = R.drawable.union
            }else {
                kapakFotografi = imageList.get(0)
            }

            var gezilecekYer = GezilecekYer(yerAdi,yerKisaTanim,kisaAciklama,null,
                kapakFotografi, oncelik)


            GezilecekYerLogic.ekle(this,gezilecekYer)


           // var arrayList = GezilecekYerLogic.tumGezilecekYerleriGetir(this)
            //Toast.makeText(this,arrayList.size.toString(), Toast.LENGTH_LONG).show()

            val intent = Intent()
            setResult(RESULT_OK, intent)
            finish()


        }




    }

    fun init() {
        imageList = ArrayList<Int>()
        val lm = LinearLayoutManager(this)
        lm.orientation = LinearLayoutManager.HORIZONTAL
        binding.rvYerEkle.layoutManager = lm

        //
        imageList.add(R.drawable.fotoekle)
        binding.rvYerEkle.adapter = YerEkleAdapter(this,imageList!!, ::deleteItem, ::addPhoto)
    }

    fun spinner() {
        val options = arrayOf("Yüksek","Orta", "Düşük")
        binding.spinner.adapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, options)
        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener,
            AdapterView.OnItemClickListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {

                when(position){
                    0 ->{
                        oncelikDurumu = "YUKSEK"
                    }
                    1-> {
                        oncelikDurumu = "ORTA"
                    }
                    2 -> {
                        oncelikDurumu= "DUSUK"
                    }
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

            override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                TODO("Not yet implemented")
            }


        }
    }

    fun deleteItem(position : Int) {
        // Fotoğraf Silme işlemi yapılacak
        Toast.makeText(this,"Silme",Toast.LENGTH_SHORT).show()

    }

    fun addPhoto(position : Int) {
        Toast.makeText(this,"Ekleme",Toast.LENGTH_SHORT).show()

        // Burada fotoğraf ekleme işlemi yapılacak. Tıklanıldığında bll ile
    }




}