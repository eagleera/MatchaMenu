package com.example.matchamenu

import android.content.res.ColorStateList
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_menu.*

class Menu : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)


        btnFav.setOnClickListener {
            btnFav.backgroundTintList =(ColorStateList.valueOf(Color.parseColor("#FFFFFF")))
        }
    }
}


