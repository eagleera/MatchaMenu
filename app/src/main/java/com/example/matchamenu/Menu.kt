package com.example.matchamenu

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast

class Menu : AppCompatActivity() {

    companion object {
        const val RESTAURANT_ID = "RESTAURANT_ID"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
        val resId = intent.getStringExtra(RESTAURANT_ID)
        Toast.makeText(this, resId.toString(), Toast.LENGTH_LONG).show()
    }
}
