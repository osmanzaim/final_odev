package com.example.final_odev.View

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.provider.MediaStore.Images.Media.getBitmap
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.final_odev.R
import com.example.final_odev.View.Adapter.YerEkleAdapter
import com.example.final_odev.databinding.ActivityZiyaretEkleBinding
import com.example.final_odev.viewmodel.FotografLogic
import com.example.final_odev.viewmodel.GezilecekYerLogic
import com.example.final_odev.viewmodel.ZiyaretLogic


class ActivityZiyaretEkle : AppCompatActivity() {
    lateinit var binding: ActivityZiyaretEkleBinding
    lateinit var imageList: ArrayList<ByteArray>

    var fotoBitmap: Bitmap?= null
    var ReqCodeCamera:Int = 0
    var tekrarGosterme = false
    var fotograf:Fotograf?=null
    lateinit var fotoUri : Uri



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityZiyaretEkleBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()



        btnZiyaretKaydetListener()

        //ziyaret ekle sayfasini devam ettir.

        //burada ziyaret ekle butonuna basıldığında artık bir konuma ziyaret edildi yani gezdiklerim listesine o konumu ekleyip, gezdiklerim fragmentini güncelleyeceğiz.

    }

    private fun btnZiyaretKaydetListener() {
        binding.btnZiyaretKaydet.setOnClickListener {

            var gezilecekYerId = intent.getIntExtra("gezilecekYerId",-1)
            var ziyaretTarihi:String = binding.ptZiyaretTarihi.text.toString()
            var ziyaretAciklamasi: String = binding.ptZiyaretAciklamasi.text.toString()
            //var kapakFotografi : ByteArray
            var ziyaret = Ziyaret(null,ziyaretTarihi,ziyaretAciklamasi,gezilecekYerId)
            ZiyaretLogic.ekle(this,ziyaret)


            //ziyaret, veritabanına kaydedildi.



            var gelenZiyaret = ZiyaretLogic.ziyaretGetirOzel(this,ziyaretTarihi,ziyaretAciklamasi,gezilecekYerId)

            GezilecekYerLogic.flagUpdate(this,1,gezilecekYerId)

            var liste = GezilecekYerLogic.flagaGoreGetir(this,1)




            for(i in 0 until imageList.size){


                fotograf = Fotograf(null,imageList[i],null,gezilecekYerId)
                fotograf!!.ziyaretAdi = gelenZiyaret.id.toString()
                //ziyaretAdi olmadigi icin tarih+gezilecek yerId yi seçtim. özel bir değer olur diye aynı tarihte aynı yeri gezmek bir defa olur gibi.

                FotografLogic.ekle(this,fotograf!!)


            }

            var imgList = FotografLogic.ziyaretAdinaGoreGetir(this,ziyaretTarihi+gezilecekYerId.toString())

            var intent = Intent()
            setResult(RESULT_OK,intent)
            finish()

            //var ziyaretList = ZiyaretLogic.fkyeGoreGetir(this,gezilecekYerId)
            //Toast.makeText(this,ziyaretList.size.toString(),Toast.LENGTH_SHORT).show()

        }
    }


    fun init() {
        imageList = ArrayList<ByteArray>()
        val lm = LinearLayoutManager(this)
        lm.orientation = LinearLayoutManager.HORIZONTAL
        binding.rvFotoEkle.layoutManager = lm

        //
        val icon = BitmapFactory.decodeResource(
            this.getResources(),
            R.drawable.fotoekle
        )

        var bytearray = DbBitmapUtility().getBytes(icon)
        imageList.add(bytearray)

        binding.rvFotoEkle.adapter = YerEkleAdapter(this,imageList!!, ::deleteItem, ::addPhoto)
    }

    fun deleteItem(position : Int) {
        // Fotoğraf Silme işlemi yapılacak
        Toast.makeText(this,"Silme", Toast.LENGTH_SHORT).show()

    }

    fun addPhoto(position : Int) {
        //Toast.makeText(this,"Ekleme", Toast.LENGTH_SHORT).show()
        adbBuilder()

        // Burada fotoğraf ekleme işlemi yapılacak. Tıklanıldığında bll ile
    }

    fun adbBuilder(){
        val alertDialogBuildier = AlertDialog.Builder(this)
        alertDialogBuildier.setTitle("Kamera veya Galeri")
        alertDialogBuildier.setMessage("Galeriden veya kameradan fotoğraf seçiniz.")

        alertDialogBuildier.setPositiveButton("Kamera") { dialog, which ->
            //kameraIzinKontrol()
        }

        alertDialogBuildier.setNegativeButton("Galeri") {dialog,which ->
            galeridenFotoSec()
        }

        alertDialogBuildier.setNeutralButton("Vazgeç") { dialog, which ->
            // Toast.makeText(this, "Vazgeçildi", Toast.LENGTH_SHORT).show()
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
                val bitmap = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                    ImageDecoder.decodeBitmap(ImageDecoder.createSource(this.contentResolver, fotoUri))
                } else {
                    MediaStore.Images.Media.getBitmap(this.contentResolver, fotoUri)
                }
                //Bitmaps.bitmap = bitmap
                fotoBitmap = bitmap
                var fotobyteArray = DbBitmapUtility().getBytes(fotoBitmap!!)
                fotograf = Fotograf(null, fotobyteArray,null,null)
                var size = imageList.size
                imageList.removeAt(size-1)

                imageList.add(fotobyteArray)
                val icon = BitmapFactory.decodeResource(
                    this.getResources(),
                    R.drawable.fotoekle
                )

                var bytearray = DbBitmapUtility().getBytes(icon)
                imageList.add(bytearray)
                binding.rvFotoEkle.adapter!!.notifyDataSetChanged()



            }else{
                // çekilen fotoğrafı bitmape dönüştürmek için aşağıdaki satır. daha sonra galeriye ekleyecegiz.

                MediaStore.Images.Media.getBitmap(this.contentResolver, fotoUri)
                //fotografiGaleriyeKaydet(bitmap)
            }


            //fotograf = Fotograf(null,fotoUri,null,null)
            //
            //
            //
            //
            // Toast.makeText(this,fotoUri.toString(),Toast.LENGTH_SHORT).show()
            //fotoList.add(f)
            //binding.rvFotograflar.adapter!!.notifyDataSetChanged()


        }
    }

}