package com.example.final_odev.View

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

import android.view.View
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.final_odev.R
import com.example.final_odev.View.Adapter.YerEkleAdapter
import com.example.final_odev.databinding.ActivityYerEkleBinding


class ActivityYerEkle : AppCompatActivity() {
    lateinit var imageList: ArrayList<Int>
    lateinit var binding : ActivityYerEkleBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityYerEkleBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
        spinner()



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
        val options = arrayOf("Öncelik ekle","Öncelik 1", "Öncelik 2", "Öncelik 3")
        binding.spinner.adapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, options)
        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener,
            AdapterView.OnItemClickListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
                when(position){
                    1 ->{
                        // öncelik 1 seçildiğinde
                    }
                    2-> {
                        // Öncelik 2 seçildiğinde
                    }
                    3 -> {
                        // Öncelik 3 seçildiğinde
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