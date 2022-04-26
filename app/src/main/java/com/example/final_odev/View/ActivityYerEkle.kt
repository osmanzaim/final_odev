package com.example.final_odev.View

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore.Images.Media.getBitmap
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
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

    lateinit var fotoUri : Uri


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityYerEkleBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
        spinner()

        btnKaydetClickListener()
        btnGeriGitListener()



    }

    private fun btnGeriGitListener() {
        binding.imageButton.setOnClickListener{
            finish()
        }
    }

    private fun btnKaydetClickListener() {
        binding.btnYerKaydet.setOnClickListener {
            var yerAdi = binding.etYerAdi.text.toString()
            var yerKisaTanim = binding.etYerKisaTanim.text.toString()
            var kisaAciklama = binding.etYerKisaAciklama.text.toString()
            var oncelik : OncelikDurumu
            val kapakFotografi : Int
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
                kapakFotografi, oncelik,id=null)


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
                        binding.imageView2.setImageResource(R.drawable.yuksek_oncelik_belirtec)

                    }
                    1-> {
                        oncelikDurumu = "ORTA"
                        binding.imageView2.setImageResource(R.drawable.orta_oncelik_belirtec)
                    }
                    2 -> {
                        oncelikDurumu= "DUSUK"
                        binding.imageView2.setImageResource(R.drawable.dusuk_oncelik_belirtec)
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
        //Toast.makeText(this,"Ekleme",Toast.LENGTH_SHORT).show()

        adbBuilder()

        // Burada fotoğraf ekleme işlemi yapılacak. Tıklanıldığında bll ile
    }


    fun adbBuilder(){
        val alertDialogBuildier = AlertDialog.Builder(this)
        alertDialogBuildier.setTitle("Kamera veya Galeri")
        alertDialogBuildier.setMessage("Galeriden veya kameradan fotoğraf seçiniz.")

        alertDialogBuildier.setPositiveButton("Kamera") { dialog, which ->

        }

        alertDialogBuildier.setNegativeButton("Galeri") {dialog,which ->
            galeridenFotoSec()
        }

        alertDialogBuildier.setNeutralButton("Vazgeç") { dialog, which ->
            Toast.makeText(this, "Vazgeçildi", Toast.LENGTH_SHORT).show()
        }

        alertDialogBuildier.show()
    }


    private fun galeridenFotoSec() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        resultLauncher.launch(intent)

    }

    var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            // There are no request codes
            val data: Intent? = result.data

            if(result.data?.data != null){
                fotoUri = result.data!!.data!! // galeriden fotograf secilirse
            }else{
                // çekilen fotoğrafı bitmape dönüştürmek için aşağıdaki satır. daha sonra galeriye ekleyecegiz.

                val bitmap = getBitmap()
                //fotografiGaleriyeKaydet(bitmap)
            }


            var f = Fotograf(null,fotoUri,null,null)
            Toast.makeText(this,fotoUri.toString(),Toast.LENGTH_SHORT).show()
            //fotoList.add(f)
            //binding.rvFotograflar.adapter!!.notifyDataSetChanged()


        }
    }


    fun getBitmap() : Bitmap {
        var source = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            ImageDecoder.createSource(this.contentResolver,fotoUri)

        } else {
            TODO("VERSION.SDK_INT < P")
        }
        var bitmap = ImageDecoder.decodeBitmap(source)

        return bitmap
    }


}