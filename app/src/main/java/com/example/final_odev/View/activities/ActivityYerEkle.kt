package com.example.final_odev.View.activities

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.provider.Settings
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.final_odev.R
import com.example.final_odev.View.Adapter.YerEkleAdapter
import com.example.final_odev.View.views.DbBitmapUtility
import com.example.final_odev.View.views.Fotograf
import com.example.final_odev.View.views.GezilecekYer
import com.example.final_odev.View.views.OncelikDurumu
import com.example.final_odev.databinding.ActivityYerEkleBinding
import com.example.final_odev.viewmodel.FotografLogic
import com.example.final_odev.viewmodel.GezilecekYerLogic
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.io.path.Path


class ActivityYerEkle : AppCompatActivity() {
    lateinit var imageList: ArrayList<ByteArray>
    lateinit var binding : ActivityYerEkleBinding
    var oncelikDurumu : String = "YUKSEK"
    lateinit var currentPhotoPath:String
    var ReqCodeCamera:Int = 0
    var tekrarGosterme = false

    var fotograf: Fotograf?=null

    var fotoBitmap: Bitmap ?= null


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

            if(fotograf == null){
                hataAlert()
            }else{
                var yerAdi = binding.etYerAdi.text.toString()
                var yerKisaTanim = binding.etYerKisaTanim.text.toString()
                var kisaAciklama = binding.etYerKisaAciklama.text.toString()
                var oncelik : OncelikDurumu
                var flagx = 0
                var kapakFotografi : ByteArray = DbBitmapUtility().getBytes(fotoBitmap!!)

                for(i in 0 until imageList.size){

                    if(i == 0){
                        kapakFotografi = imageList[i]
                    }
                    fotograf = Fotograf(null,imageList[i],null,null)
                    fotograf!!.yerAdi = yerAdi
                    fotograf!!.ziyaretAdi = null
                    FotografLogic.ekle(this,fotograf!!)
                }






                if(oncelikDurumu == OncelikDurumu.YUKSEK.toString()){
                    oncelik = OncelikDurumu.YUKSEK
                }else if(oncelikDurumu == OncelikDurumu.ORTA.toString()){
                    oncelik = OncelikDurumu.ORTA
                }else{
                    oncelik = OncelikDurumu.DUSUK
                }
                /*
                 if(imageList.size == 1) {
                    kapakFotografi = R.drawable.union
                }else {
                    kapakFotografi = imageList.get(0)
                }
                 */





                var gezilecekYer = GezilecekYer(yerAdi,yerKisaTanim,kisaAciklama,null,
                    kapakFotografi, oncelik,id=null, flag=flagx)

                fotograf!!.yerAdi = yerAdi


                //FotografLogic.ekle(this,fotograf!!)
                //veriyi çekerken yugulama çöküyor.
                var liste = FotografLogic.yerAdinaGoreGetir(this,yerAdi)
                //Toast.makeText(this,liste.size.toString(),Toast.LENGTH_SHORT).show()



                //bytearray olarak veritabanına kaydettik.
                //veritabanından alırken de bytearray olarak alıp, gezilecek yer classına verdik.
                //şimdi bytearrayi bitmape donusturup imageviewa atmamız gerekiyor.


                /*
                 if(::fotoUri.isInitialized){
                    var gezilecekYer = GezilecekYer(yerAdi,yerKisaTanim,kisaAciklama,null,
                        fotoUri.toString(), oncelik,id=null)
                }else{
                    var gezilecekYer = GezilecekYer(yerAdi,yerKisaTanim,kisaAciklama,null,
                        kapakFotografi, oncelik,id=null)
                }
                 */




                GezilecekYerLogic.ekle(this,gezilecekYer)


                //var arrayList = GezilecekYerLogic.tumGezilecekYerleriGetir(this)
                //Toast.makeText(this,arrayList.size.toString(), Toast.LENGTH_LONG).show()



                val intent = Intent()
                setResult(RESULT_OK, intent)
                finish()


            }

        }





    }

    fun hataAlert(){

        val builder = AlertDialog.Builder(this)
        builder.setTitle("Fotoğraf bulunamadı")
        builder.setMessage("Fotoğraf ekleyin.")
//builder.setPositiveButton("OK", DialogInterface.OnClickListener(function = x))

        builder.setPositiveButton("Tamam") { dialog, which ->

        }

        builder.show()
    }

    fun init() {
        imageList = ArrayList<ByteArray>()
        val lm = LinearLayoutManager(this)
        lm.orientation = LinearLayoutManager.HORIZONTAL
        binding.rvYerEkle.layoutManager = lm

        //
        //imageList.add(R.drawable.fotoekle)
        //imageList.add(R.drawable.fotoekle)
        //imageList.add(R.drawable.fotoekle)
        val icon = BitmapFactory.decodeResource(
            this.getResources(),
            R.drawable.fotoekle
        )

        var bytearray = DbBitmapUtility().getBytes(icon)
        imageList.add(bytearray)
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
            kameraIzinKontrol()
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

    @RequiresApi(Build.VERSION_CODES.O)
    var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            // There are no request codes
            val data: Intent? = result.data

            if(result.data?.data != null){
                fotoUri = result.data!!.data!! // galeriden fotograf secilirse
                var s = fotoUri.toString()
                var x = Uri.parse(s)
                val bitmap2 = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                    ImageDecoder.decodeBitmap(ImageDecoder.createSource(this.contentResolver, x))
                } else {
                    MediaStore.Images.Media.getBitmap(this.contentResolver, x)
                }

                binding.ivDeneme.setImageBitmap(bitmap2)



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
                binding.rvYerEkle.adapter!!.notifyDataSetChanged()


            }else{
                // çekilen fotoğrafı bitmape dönüştürmek için aşağıdaki satır. daha sonra galeriye ekleyecegiz.

                val bitmap = getBitmap()
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


    fun getBitmap() : Bitmap {
        var source = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            ImageDecoder.createSource(this.contentResolver,fotoUri)

        } else {
            TODO("VERSION.SDK_INT < P")
        }
        var bitmap = ImageDecoder.decodeBitmap(source)

        return bitmap
    }




    fun kameraIzinKontrol()
    {
        val requestList = java.util.ArrayList<String>()
        var izinDurum = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
        var izinDurum2 = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
        var izinDurum3 = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED

        if(!izinDurum) requestList.add(Manifest.permission.CAMERA)
        if(!izinDurum2) requestList.add(Manifest.permission.READ_EXTERNAL_STORAGE)
        if(!izinDurum3) requestList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)

        /*
        if(!izinDurum && !izinDurum2 && !izinDurum3){
            requestList.add(Manifest.permission.CAMERA)
            requestList.add(Manifest.permission.READ_EXTERNAL_STORAGE)
            requestList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }*/

        if (requestList.size == 0)  kameraAc()
        else requestPermissions(requestList.toTypedArray(), ReqCodeCamera)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        var tumuOnaylandi = true
        for (gr in grantResults)
        {
            if (gr != PackageManager.PERMISSION_GRANTED)
            {
                tumuOnaylandi = false
                break
            }
        }

        if(!tumuOnaylandi){
            //var tekrarGosterme = false

            for (permission in permissions)
            {
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission))
                else if (ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED)
                else
                {
                    tekrarGosterme = true
                    break
                }
            }
            if (tekrarGosterme)
            {
                val adb = AlertDialog.Builder(this)
                adb.setTitle("İzin Gerekli")
                    .setMessage("Ayarlara giderek tüm izinleri onaylayınız")
                    .setPositiveButton("Ayarlar") { dialog, which ->
                        ayarlarAc()
                    }
                    .setNegativeButton("Vazgeç", null)
                    .show()
            }
        }else{
            when(requestCode){
                ReqCodeCamera ->kameraAc()
            }
        }


    }
    private fun ayarlarAc() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        val uri = Uri.fromParts("package", packageName, null)
        intent.data = uri

        startActivity(intent)
    }

    private fun kameraAc() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        val dosyaYolu = resimKlasoruOlustur()

        fotoUri  = FileProvider.getUriForFile(this,packageName,dosyaYolu)
        //fotoUri'resultlauncherde değişmiyor. listeye burada atsak ne olur?
        // cevabı -olur
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fotoUri)
        resultLauncher.launch(intent)
    }

    @SuppressLint("SimpleDateFormat")
    @Throws(IOException::class)
    private fun resimKlasoruOlustur(): File {
        // Create an image file name
        val storageDir: File = getExternalFilesDir(Environment.DIRECTORY_PICTURES)!!
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())

        return File.createTempFile(timeStamp,".jpg", storageDir).apply{
            currentPhotoPath = absolutePath
        }
    }


    companion object Bitmaps {
        lateinit var bitmap: Bitmap
    }

}

