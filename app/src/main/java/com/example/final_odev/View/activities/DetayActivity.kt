package com.example.final_odev.View.activities

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.final_odev.R
import com.example.final_odev.View.*
import com.example.final_odev.View.Adapter.SliderAdapter
import com.example.final_odev.View.Adapter.ZiyaretAdapter
import com.example.final_odev.View.views.Fotograf
import com.example.final_odev.View.views.GezilecekYer
import com.example.final_odev.View.views.OncelikDurumu
import com.example.final_odev.View.views.Ziyaret
import com.example.final_odev.databinding.ActivityDetayBinding
import com.example.final_odev.databinding.TabLayoutBinding
import com.example.final_odev.fragments.GezdiklerimFragment
import com.example.final_odev.fragments.GezileceklerFragment
import com.example.final_odev.viewmodel.FotografLogic
import com.example.final_odev.viewmodel.GezilecekYerLogic
import com.example.final_odev.viewmodel.ZiyaretLogic
import com.google.android.material.tabs.TabLayoutMediator

class DetayActivity : AppCompatActivity() {
    lateinit var binding: ActivityDetayBinding
    lateinit var ziyaretListesi: ArrayList<Ziyaret>
    lateinit var yerAdi:String
    lateinit var gezilecekYer: GezilecekYer
    var count = 0
    var imgList :ArrayList<Fotograf>?= null

    var fotoList = arrayListOf<Fotograf>()

    var gezdigimYerlerListesi = arrayListOf<GezilecekYer>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetayBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewPagerOlustur()
        TabLayoutMediator(binding.tabLayout,binding.viewPager2){
                tab,position ->
        }.attach()
        tabOlustur()



        gelenClassaGoreVeriDoldur()
        ziyaretDoldur()
        ziyaretGecmisiRvHazirla()
        imgSliderOlustur()
        btnZiyaretEkleListener()

        btnGeriOkuListener()

    }

    private fun btnGeriOkuListener() {
        binding.imageView.setOnClickListener {
            finish()
        }
    }


    private fun ziyaretGecmisiRvHazirla() {
        binding.rvZiyaretGecmisi.apply{
            layoutManager = GridLayoutManager(applicationContext,1)
            adapter = ZiyaretAdapter(ziyaretListesi)
        }
    }

    private fun ziyaretDoldur() {
        var ziyaretId = intent.getIntExtra("id",-1)
        ziyaretListesi = ZiyaretLogic.fkyeGoreGetir(this,ziyaretId)

        if(ziyaretListesi.size == 0){
            binding.rvZiyaretGecmisi.visibility = View.GONE
            binding.textView2.visibility = View.GONE
        }
    }

    private fun btnZiyaretEkleListener() {

        binding.btnZiyaretEkle.setOnClickListener {
            var i = Intent(this, ActivityZiyaretEkle::class.java)
            var ziyaretId = intent.getIntExtra("id",-1)
            i.putExtra("gezilecekYerId",ziyaretId)
            startForResult.launch(i)
        }

    }

    fun gelenClassaGoreVeriDoldur() {

        var id = intent.getIntExtra("id",-1)
        //Toast.makeText(this,id.toString(),Toast.LENGTH_SHORT).show()
        gezilecekYer = GezilecekYerLogic.idyeGoreGetir(this,id)
        yerAdi = gezilecekYer.yerAdi

        binding.tvYerBasligi.text = gezilecekYer.yerAdi

        if(gezilecekYer.oncelik == OncelikDurumu.YUKSEK){
            binding.ivOncelikDurumu.setImageResource(R.drawable.yuksek_oncelik_belirtec)
        }else if(gezilecekYer.oncelik == OncelikDurumu.ORTA){
            binding.ivOncelikDurumu.setImageResource(R.drawable.orta_oncelik_belirtec)
        }else{
            binding.ivOncelikDurumu.setImageResource(R.drawable.dusuk_oncelik_belirtec)
        }

        binding.tvKisaTanimBilgisi.text = gezilecekYer.kisaTanim
        binding.tvKisaAciklamaBilgisi.text = gezilecekYer.aciklama

        // veriyi ald??k.
        //Toast.makeText(this,gezilecekYer.kisaTanim,Toast.LENGTH_LONG).show()
    }
    fun geri(){
        if(count == 0) {
            count = imgList!!.size-1
            binding.rvFotoSlider.scrollToPosition(count)


        }else {
            count -= 1
            binding.rvFotoSlider.scrollToPosition(count)
        }
    }
    fun ileri(){
        if(count < imgList!!.size) {
            count += 1
            binding.rvFotoSlider.scrollToPosition(count)
        }
        if(count == imgList!!.size){
            count = 0
            binding.rvFotoSlider.scrollToPosition(count)
        }
    }

    fun imgSliderOlustur() {

        var geldigiFragment = intent.getStringExtra("geldigiFragment")

        if(geldigiFragment == null){ //gezileceklerden geldiyse detay sayfas??
            imgList = FotografLogic.yerAdinaGoreGetir(this,yerAdi)
            binding.rvFotoSlider.apply{
                var lm = object : LinearLayoutManager(this@DetayActivity){
                    override fun canScrollHorizontally(): Boolean {
                        return false
                    }
                }
                lm.orientation = LinearLayoutManager.HORIZONTAL
                layoutManager = lm

                var gelenVeri = intent.getStringExtra("durum")
                var tarih = intent.getStringExtra("tarih")

                adapter = SliderAdapter(this@DetayActivity,imgList!!,tarih,gelenVeri!!,::geri,::ileri)
            }
        }else { //gezdiklerimden geldiyse detay sayfas??.

            var ziyaretinFotograflari :ArrayList<Fotograf> = arrayListOf()
            var yerId = intent.getIntExtra("id",-1)

            if(yerId != -1){
                imgList = arrayListOf()
               var x = FotografLogic.fkyeGore(this,yerId)

                for (item in x){
                    ziyaretinFotograflari.add(item)
                    imgList!!.add(item)
                }
            }



            binding.rvFotoSlider.apply{
                var lm = object : LinearLayoutManager(this@DetayActivity){
                    override fun canScrollHorizontally(): Boolean {
                        return false
                    }
                }
                lm.orientation = LinearLayoutManager.HORIZONTAL
                layoutManager = lm
                var gelenVeri = intent.getStringExtra("durum")


                var tarih = intent.getStringExtra("tarih")
                adapter = SliderAdapter(this@DetayActivity,ziyaretinFotograflari!!,tarih,gelenVeri!!,::geri,::ileri)
            }






            //imageList.add(SlideModel(R.drawable.karagol,""))
            //imageList.add(SlideModel(R.drawable.mencuna_selalesi,""))
            //imageList.add(SlideModel(R.drawable.sumela_manastiri,""))


        }

        //imageList.add(SlideModel(R.drawable.karagol,""))
        //imageList.add(SlideModel(R.drawable.mencuna_selalesi,""))
        //imageList.add(SlideModel(R.drawable.sumela_manastiri,""))


    }
    private fun viewPagerOlustur() {
        val adapter = MainActivity.ViewPagerAdapter(this)
        adapter.fragmentEkle(GezileceklerFragment())
        adapter.fragmentEkle(GezdiklerimFragment())

        binding.viewPager2.adapter = adapter
    }


    private fun tabOlustur() {

        var tab = TabLayoutBinding.inflate(layoutInflater)
        tab.tvBaslik.text = "Gezilecekler"
        tab.imageView.setImageResource(R.drawable.home)
        binding.tabLayout.getTabAt(0)!!.setCustomView(tab.root)

        tab = TabLayoutBinding.inflate(layoutInflater)
        tab.tvBaslik.text = "Gezdiklerim"
        tab.imageView.setImageResource(R.drawable.person)
        binding.tabLayout.getTabAt(1)!!.setCustomView(tab.root)


    }

    @SuppressLint("NotifyDataSetChanged")
    val startForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult())
    { result: ActivityResult ->
        if (result.resultCode == Activity.RESULT_OK) {
            //  you will get result here in result.data
            var ziyaretId = intent.getIntExtra("id",-1)
            ziyaretListesi = ZiyaretLogic.fkyeGoreGetir(this,ziyaretId)

            binding.rvZiyaretGecmisi.visibility = View.VISIBLE
            binding.textView2.visibility = View.VISIBLE
            binding.rvZiyaretGecmisi.adapter = ZiyaretAdapter(ziyaretListesi)


        }

    }
}
